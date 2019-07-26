package com.android.photoalbum.repository

import com.android.photoalbum.model.PhotosModel
import com.android.photoalbum.repository.core.APIService
import com.android.photoalbum.repository.core.execute
import com.android.photoalbum.repository.listener.RepoResponseListener

class PhotosRepoImp(private val apiService: APIService) : PhotosRepo {

    override fun getAllPhotos(repoResponseListener: RepoResponseListener<List<PhotosModel>>) {
        apiService.getAllPhotos().execute(repoResponseListener)
    }
}