package com.android.photoalbum.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.photoalbum.R
import com.android.photoalbum.model.AlbumsDetails
import kotlinx.android.synthetic.main.main_row.view.*

class AlbumsAdapter(private val context: Context) : RecyclerView.Adapter<AlbumsAdapter.AlbumDataViewHolder>() {

    private var albumsList: List<AlbumsDetails>? = null

    fun setAlbumsData(albums: List<AlbumsDetails>) {
        albumsList = albums
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumDataViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.main_row,parent,false)
        return AlbumDataViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (albumsList != null && albumsList!!.isNotEmpty()) {
            albumsList!!.size
        } else {
            0
        }
    }

    override fun onBindViewHolder(holder: AlbumDataViewHolder, position: Int) {
        if (albumsList != null) {
            holder.setData(context,albumsList!![position])
        }
    }


    class AlbumDataViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem) {

        fun setData(context: Context, album: AlbumsDetails) {
            itemView.albumIdTxt.text = "Album ID: ".plus(album.albumId)

            // bindData
            val linearLayoutManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
            itemView.list_recycler.layoutManager = linearLayoutManager

            val listAdapter = PhotoListAdapter()
            listAdapter.setPhotos(album.albumList)

            itemView.list_recycler.adapter = listAdapter
        }
    }
}