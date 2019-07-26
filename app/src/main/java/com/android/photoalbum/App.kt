package com.android.photoalbum

import android.app.Application
import com.android.photoalbum.repository.PhotosRepo
import com.android.photoalbum.repository.PhotosRepoImp
import com.android.photoalbum.repository.core.ServerInjector

class App : Application() {

    companion object {
        var instance: App? = null
        private val apiService = ServerInjector.getInstance().provideApiService()
        private var photosRepo: PhotosRepo? = null

        // Dagger can be used here for dependency Injection
        fun getPhotosRepository(): PhotosRepo? {
            if (photosRepo == null) {
                photosRepo = PhotosRepoImp(apiService)
            }
            return photosRepo
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}