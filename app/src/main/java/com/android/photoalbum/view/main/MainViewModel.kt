package com.android.photoalbum.view.main

import androidx.lifecycle.LiveData
import com.android.photoalbum.App
import com.android.photoalbum.model.PhotosModel
import com.android.photoalbum.viewmodel.BaseViewModel

class MainViewModel : BaseViewModel() {
    private val photosRepository = App.getPhotosRepository()!!

    fun getAllAlbums(): LiveData<List<PhotosModel>> {
        return photosRepository.getAllAlbums()
    }

    fun fetchAlbumsFromServer() {
        photosRepository.getAllPhotosFromServer(getStateLiveData())
    }
}