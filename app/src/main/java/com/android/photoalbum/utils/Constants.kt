package com.android.photoalbum.utils

object Constants {
    const val API_BASE_URL: String = "http://jsonplaceholder.typicode.com/"
    const val GET_PHOTOS_METHOD = "photos"
}

object RoomConfig {
    const val DATABASE_PHOTO_ALBUM = "photo_album.db"
    const val TABLE_PHOTOS = "photos"

    private const val SELECT_FROM = "SELECT * FROM "

    const val SELECT_ALL_PHOTOS = SELECT_FROM + TABLE_PHOTOS
}