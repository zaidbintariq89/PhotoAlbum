package com.android.photoalbum

import android.app.Application
import com.android.photoalbum.repository.PhotosRepo
import com.android.photoalbum.repository.PhotosRepoImp
import com.android.photoalbum.repository.core.ServerInjector
import com.android.photoalbum.repository.db.RoomDataSource

class App : Application() {

    companion object {
        lateinit var instance: App
        private val apiService = ServerInjector.getInstance().provideApiService()
        private var photosRepo: PhotosRepo? = null

        // Dagger can be used here for dependency Injection
        fun getPhotosRepository(): PhotosRepo? {
            if (photosRepo == null) {
                photosRepo = PhotosRepoImp(apiService, getAppDataBase())
            }
            return photosRepo
        }

        private fun getAppDataBase(): RoomDataSource {
            return RoomDataSource.getInstance(instance)
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}