package com.android.photoalbum.model

data class AlbumsDetails(
    var albumId: Int,
    val albumList: List<PhotosModel>
)