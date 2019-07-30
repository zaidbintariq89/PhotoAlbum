package com.android.photoalbum.repository.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.android.photoalbum.model.AlbumsDetails
import com.android.photoalbum.model.PhotosModel
import com.android.photoalbum.utils.RoomConfig
import io.reactivex.Flowable

@Dao
interface PhotosDao {

    @Insert
    fun insertAllPhotoAlbums(photos: List<PhotosModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAnAlbum(album: PhotosModel)

    @Query(RoomConfig.SELECT_ALL_PHOTOS)
    fun getAll(): LiveData<List<PhotosModel>>

    // for Testing purpose
    @Query(RoomConfig.SELECT_ALL_PHOTOS)
    fun getAllAlbums(): List<PhotosModel>

    @Query("SELECT * FROM photos WHERE albumId = :photoId")
    fun loadAllAlbumsByIds(photoId: Int): List<PhotosModel>

    @Query("SELECT DISTINCT albumId FROM photos")
    fun getDistinctAlbumIds(): IntArray

    @Delete
    fun delete(album: PhotosModel)

    @Query("DELETE FROM photos")
    fun deleteAll()

    @Transaction
    fun updateContentsInDb(photos: List<PhotosModel>) {
        deleteAll()
        insertAllPhotoAlbums(photos)
    }

    @Transaction
    fun getAlbumsByGroup(): List<AlbumsDetails> {
        val listAlbums = ArrayList<AlbumsDetails>()

        val albumIds = getDistinctAlbumIds()
        albumIds.forEach {
            val list = loadAllAlbumsByIds(it)
            listAlbums.add(AlbumsDetails(it, list))
        }
        return listAlbums
    }

    // get All data with Rx
    @Query(RoomConfig.SELECT_ALL_PHOTOS)
    fun getAllPhotosRx() : Flowable<List<PhotosModel>>
}