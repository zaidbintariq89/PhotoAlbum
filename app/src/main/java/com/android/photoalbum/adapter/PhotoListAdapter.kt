package com.android.photoalbum.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.photoalbum.R
import com.android.photoalbum.model.PhotosModel
import com.android.photoalbum.utils.loadImage
import kotlinx.android.synthetic.main.list_row.view.*

class PhotoListAdapter: RecyclerView.Adapter<PhotoListAdapter.PhotoViewHolder>() {

    private var photoList: List<PhotosModel>? = null

    fun setPhotos(photos: List<PhotosModel>) {
        photoList = photos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_row,parent,false)
        return PhotoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (photoList != null && photoList!!.isNotEmpty()) {
            photoList!!.size
        } else {
            0
        }
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        if (photoList != null) {
            holder.setData(photoList!![position])
        }
    }


    class PhotoViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem) {

        fun setData(photosModel: PhotosModel) {
            if (photosModel.thumbnailUrl.isNotEmpty()) {
                itemView.thumb.loadImage(photosModel.thumbnailUrl)
            }
        }
    }
}