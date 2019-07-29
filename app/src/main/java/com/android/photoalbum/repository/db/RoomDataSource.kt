package com.android.photoalbum.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.photoalbum.model.PhotosModel
import com.android.photoalbum.utils.RoomConfig

@Database(entities = [PhotosModel::class], version = 1)
abstract class RoomDataSource : RoomDatabase() {

    abstract fun photosDao(): PhotosDao

    companion object {

        @Volatile private var INSTANCE: RoomDataSource? = null

        fun getInstance(context: Context): RoomDataSource =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                RoomDataSource::class.java, RoomConfig.DATABASE_PHOTO_ALBUM)
                .build()
    }
}