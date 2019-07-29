package com.android.photoalbum.view.main

import androidx.lifecycle.MutableLiveData
import com.android.photoalbum.App
import com.android.photoalbum.model.AlbumsDetails
import com.android.photoalbum.viewmodel.BaseViewModel

class MainViewModel : BaseViewModel() {
    private val photosRepository = App.getPhotosRepository()!!
    val albumsDetailsLV = MutableLiveData<List<AlbumsDetails>>()

    fun getAllAlbums() {
        photosRepository.getAllAlbums(albumsDetailsLV)
    }

    fun fetchAlbumsFromServer() {
        photosRepository.getAllPhotosFromServer(getStateLiveData())
    }
}