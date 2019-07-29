package com.android.photoalbum.viewstate

abstract class ViewState

data class ApiCallViewState(val loading: Boolean, val error: String?, val data: Any?) : ViewState()