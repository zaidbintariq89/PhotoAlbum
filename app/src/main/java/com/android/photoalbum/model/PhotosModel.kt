package com.android.photoalbum.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.photoalbum.utils.RoomConfig
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = RoomConfig.TABLE_PHOTOS)
data class PhotosModel(
    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0,
    @SerializedName("albumId")
    val albumId: Int = 0,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("url")
    val url: String = "",
    @SerializedName("thumbnailUrl")
    val thumbnailUrl: String = ""
)