package com.android.photoalbum.repository.db

import androidx.room.*
import com.android.photoalbum.model.PhotosModel
import com.android.photoalbum.utils.RoomConfig

@Dao
interface PhotosDao {

    @Insert
    fun insertAllPhotoAlbums(photos: List<PhotosModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAnAlbum(album: PhotosModel)

    @Query(RoomConfig.SELECT_ALL_PHOTOS)
    fun getAll(): List<PhotosModel>

    @Query("SELECT * FROM photos WHERE id IN (:photoIds)")
    fun loadAllAlbumsByIds(photoIds: IntArray): List<PhotosModel>

    @Delete
    fun delete(album: PhotosModel)

    @Query("DELETE FROM photos")
    fun deleteAll()
}