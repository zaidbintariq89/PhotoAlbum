package com.android.photoalbum.view.main

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.photoalbum.R
import com.android.photoalbum.model.PhotosModel
import com.android.photoalbum.utils.showToast
import com.android.photoalbum.view.AppBaseActivity
import com.android.photoalbum.viewmodel.BaseViewModel
import com.android.photoalbum.viewstate.ApiCallViewState
import com.android.photoalbum.viewstate.ViewState
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppBaseActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var mainViewModel: MainViewModel


    override fun getLayoutFile(): Int {
        return R.layout.activity_main
    }

    override fun initViewModel(viewModelProvider: ViewModelProvider): BaseViewModel? {
        mainViewModel = viewModelProvider.get(MainViewModel::class.java)
        return mainViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        swipeRefreshView.setOnRefreshListener(this)
        // get photos
        loadAllPhotos()
    }

    private fun loadAllPhotos() {
        mainViewModel.getAllAlbums().observe(this, Observer { albums ->
            if (albums.isEmpty()) {
                mainViewModel.fetchAlbumsFromServer()
            } else {
                bindAlbumsAdapter(albums)
            }
        })
    }

    private fun bindAlbumsAdapter(albums: List<PhotosModel>) {

    }

    override fun render(state: ViewState) {
        when (state) {
            is ApiCallViewState -> {
                swipeRefreshView.isRefreshing = state.loading

                if (!state.error.isNullOrEmpty()) {
                    showToast(state.error)
                }
            }
        }
    }

    override fun onRefresh() {
        mainViewModel.fetchAlbumsFromServer()
    }
}
