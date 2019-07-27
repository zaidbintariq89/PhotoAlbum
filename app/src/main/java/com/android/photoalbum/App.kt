package com.android.photoalbum

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.android.photoalbum.repository.PhotosRepo
import com.android.photoalbum.repository.PhotosRepoImp
import com.android.photoalbum.repository.core.ServerInjector
import com.android.photoalbum.repository.db.RoomDataSource
import com.android.photoalbum.utils.RoomConfig

class App : Application() {

    companion object {
        lateinit var instance: App
        private val apiService = ServerInjector.getInstance().provideApiService()
        private var photosRepo: PhotosRepo? = null
        private var roomDataSource: RoomDataSource? = null

        // Dagger can be used here for dependency Injection
        fun getPhotosRepository(): PhotosRepo? {
            if (photosRepo == null) {
                photosRepo = PhotosRepoImp(apiService, getAppDataBase()!!)
            }
            return photosRepo
        }

        private fun getAppDataBase(): RoomDataSource? {
            if (roomDataSource == null) {
                roomDataSource = buildDatabase(instance)
            }

            return roomDataSource
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                RoomDataSource::class.java, RoomConfig.DATABASE_PHOTO_ALBUM
            )
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}