package com.android.photoalbum.view.main

import com.android.photoalbum.App
import com.android.photoalbum.model.AlbumsDetails
import com.android.photoalbum.repository.listener.RepoResponseListener
import com.android.photoalbum.viewmodel.BaseViewModel
import com.android.photoalbum.viewstate.ApiCallViewState
import io.reactivex.disposables.CompositeDisposable

class MainViewModel : BaseViewModel() {
    val subscription = CompositeDisposable()

    fun getAllAlbumsRx() {
        getStateLiveData().value = ApiCallViewState(true, null, null)
        subscription.add(App.getNetworkRepository().fetchUsers(
            object : RepoResponseListener<List<AlbumsDetails>>() {
                override fun onSuccess(response: List<AlbumsDetails>) {
                    getStateLiveData().value = ApiCallViewState(false, null, response)
                }

                override fun onError(error: String) {
                    getStateLiveData().value = ApiCallViewState(false, error, null)
                }
            }
        ))
    }
}