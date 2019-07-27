package com.android.photoalbum.repository.core

import com.android.photoalbum.App
import com.android.photoalbum.R
import com.android.photoalbum.utils.isConnectedToInternet
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class HeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val app = App.instance
        if (!app.isConnectedToInternet()) {
            throw IOException(app.getString(R.string.internet_not_available))
        }

        var request = chain.request()
        request = request.newBuilder()
            .header("Content-Type", "application/json")
            .build()
        return chain.proceed(request)
    }
}