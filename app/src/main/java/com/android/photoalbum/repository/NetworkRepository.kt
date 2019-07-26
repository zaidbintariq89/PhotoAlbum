package com.android.photoalbum.repository

import com.android.photoalbum.model.PhotosModel
import com.android.photoalbum.repository.core.ServerInjector
import com.android.photoalbum.repository.core.execute
import com.android.photoalbum.repository.listener.RepoResponseListener

object NetworkRepository {
    // this can be injected using Dagger
    private val apiService = ServerInjector.getInstance().provideApiService()

    fun getAllPhotos(repoResponseListener: RepoResponseListener<List<PhotosModel>>) {
        apiService.getAllPhotos().execute(repoResponseListener)
    }
}