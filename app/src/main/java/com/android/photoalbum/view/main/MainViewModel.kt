package com.android.photoalbum.view.main

import com.android.photoalbum.App
import com.android.photoalbum.viewmodel.BaseViewModel

class MainViewModel : BaseViewModel() {
    private val photosRepository = App.getPhotosRepository()!!

    fun getAllAlbums(forceRefresh: Boolean) {
        photosRepository.getAllAlbums(forceRefresh, getStateLiveData())
    }
}