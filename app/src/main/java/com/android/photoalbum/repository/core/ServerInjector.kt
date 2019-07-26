package com.android.photoalbum.repository.core

import com.android.photoalbum.BuildConfig
import com.android.photoalbum.repository.listener.RepoResponseListener
import com.android.photoalbum.utils.Constants
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// Singleton class for network requests
class ServerInjector private constructor() {

    companion object {
        @Volatile
        private var INSTANCE: ServerInjector? = null

        fun getInstance(): ServerInjector {
            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = ServerInjector()
                }
            }
            return INSTANCE!!
        }
    }

    private fun getOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        val builder = OkHttpClient.Builder()
        builder.addInterceptor(interceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(HeaderInterceptor())
        return builder.build()
    }

    private fun provideRetrofit(): Retrofit {
        val httpClient = getOkHttpClient()
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder()
            .baseUrl(Constants.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient)
            .build()
    }

    fun provideApiService(): APIService {
        return provideRetrofit()
            .create(APIService::class.java)
    }
}

// create Extension for Network Call
fun <T> Call<T>.execute(responseListener: RepoResponseListener<T>) {
    this.enqueue(object : Callback<T> {
        override fun onFailure(call: Call<T>, t: Throwable) {
            responseListener.onError(t.localizedMessage)
        }

        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.isSuccessful) {
                responseListener.onSuccess(response = response.body()!!)
            } else if (response.errorBody() != null) {
                val errorBody = response.errorBody()!!.string()
                val error = parseErrorBody(errorBody)
                responseListener.onError(error)
            } else {
                responseListener.onError("Something went wrong.\nPlease try again.")
            }
        }
    })
}

fun parseErrorBody(errorBody: String): String {
    var errorMsg = ""
    try {
        val json = JSONObject(errorBody)
        if (json.has("message")) {
            errorMsg = json.getString("message")
        } else if (json.has("error_description")) {
            errorMsg = json.getString("error_description")
        }
    } catch (e: JSONException) {
        errorMsg = ""
    }
    return errorMsg
}