package com.android.photoalbum.repository

import com.android.photoalbum.model.PhotosModel
import com.android.photoalbum.repository.listener.RepoResponseListener

interface PhotosRepo {
    fun getAllPhotos(repoResponseListener: RepoResponseListener<List<PhotosModel>>)
}