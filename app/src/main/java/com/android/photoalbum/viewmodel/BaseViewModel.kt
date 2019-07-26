package com.android.photoalbum.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.photoalbum.viewstate.ViewState

abstract class BaseViewModel : ViewModel() {

    private val stateLiveData: MutableLiveData<ViewState> = MutableLiveData()

    fun getStateLiveData(): MutableLiveData<ViewState> {
        return stateLiveData
    }
}