package com.android.photoalbum.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.photoalbum.model.PhotosModel

@Database(entities = [PhotosModel::class], version = 1)
abstract class RoomDataSource : RoomDatabase() {

    abstract fun photosDao(): PhotosDao
}