package com.android.photoalbum.repository.core

import com.android.photoalbum.model.PhotosModel
import com.android.photoalbum.utils.Constants
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET


interface APIService {

    @GET(Constants.GET_PHOTOS_METHOD)
    fun getAllPhotos() : Call<List<PhotosModel>>

    @GET(Constants.GET_PHOTOS_METHOD)
    fun getAllPhotosRX() : Observable<List<PhotosModel>>
}