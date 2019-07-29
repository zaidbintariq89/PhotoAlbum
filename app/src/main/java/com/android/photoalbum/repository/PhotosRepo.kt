package com.android.photoalbum.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.photoalbum.model.PhotosModel
import com.android.photoalbum.viewstate.ViewState

interface PhotosRepo {
    fun getAllPhotos() : List<PhotosModel>
    fun getAllAlbums(): LiveData<List<PhotosModel>>
    fun getAllPhotosFromServer(stateLiveData: MutableLiveData<ViewState>)
}