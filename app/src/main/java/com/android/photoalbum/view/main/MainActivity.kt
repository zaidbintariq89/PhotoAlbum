package com.android.photoalbum.view.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.android.photoalbum.R
import com.android.photoalbum.view.AppBaseActivity
import com.android.photoalbum.viewmodel.BaseViewModel
import com.android.photoalbum.viewstate.ViewState

class MainActivity : AppBaseActivity() {

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
        // get photos
        mainViewModel.getAllPhotos()
    }

    override fun render(state: ViewState) {

    }
}
