package com.android.photoalbum.repository

import androidx.lifecycle.MutableLiveData
import com.android.photoalbum.model.AlbumsDetails
import com.android.photoalbum.model.PhotosModel
import com.android.photoalbum.viewstate.ViewState

interface PhotosRepo {
    fun getAllPhotos() : List<PhotosModel>
    fun getAllAlbums(albumsLV :MutableLiveData<List<AlbumsDetails>>)
    fun getAllPhotosFromServer(stateLiveData: MutableLiveData<ViewState>)
}