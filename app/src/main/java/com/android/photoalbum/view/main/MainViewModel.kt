package com.android.photoalbum.view.main

import android.util.Log
import com.android.photoalbum.model.PhotosModel
import com.android.photoalbum.repository.NetworkRepository
import com.android.photoalbum.repository.listener.RepoResponseListener
import com.android.photoalbum.viewmodel.BaseViewModel

class MainViewModel : BaseViewModel() {

    fun getAllPhotos() {

        NetworkRepository.getAllPhotos(
            object : RepoResponseListener<List<PhotosModel>>() {
                override fun onSuccess(response: List<PhotosModel>) {
                    Log.d("Main-View-Model",response[0].title)
                }

                override fun onError(error: String) {
                    Log.d("Main-View-Model-Error",error)
                }
            }
        )
    }
}