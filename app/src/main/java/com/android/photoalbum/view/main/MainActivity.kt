package com.android.photoalbum.view.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.photoalbum.R
import com.android.photoalbum.adapter.AlbumsAdapter
import com.android.photoalbum.model.AlbumsDetails
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
        mainViewModel.getAllAlbumsRx()
    }

    private fun bindAlbumsAdapter(albums: List<AlbumsDetails>) {
        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
        albums_recycler.layoutManager = linearLayoutManager

        val adapter = AlbumsAdapter(this)
        adapter.setAlbumsData(albums)

        albums_recycler.adapter = adapter
    }

    override fun render(state: ViewState) {
        when (state) {
            is ApiCallViewState -> {
                swipeRefreshView.isRefreshing = state.loading

                if (!state.error.isNullOrEmpty()) {
                    showToast(state.error)
                }

                if (state.data != null && state.data is List<*>) {
                    bindAlbumsAdapter(state.data as List<AlbumsDetails>)
                }
            }
        }
    }

    override fun onRefresh() {
        mainViewModel.getAllAlbumsRx()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainViewModel.subscription.clear()
    }
}
