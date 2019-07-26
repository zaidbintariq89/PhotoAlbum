package com.android.photoalbum.repository.listener

abstract class RepoResponseListener<T> {

    abstract fun onSuccess(response: T)
    abstract fun onError(error: String)
}