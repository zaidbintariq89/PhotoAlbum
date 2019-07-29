package com.android.photoalbum.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.photoalbum.model.PhotosModel
import com.android.photoalbum.repository.core.APIService
import com.android.photoalbum.repository.core.execute
import com.android.photoalbum.repository.db.RoomDataSource
import com.android.photoalbum.repository.listener.RepoResponseListener
import com.android.photoalbum.viewstate.ApiCallViewState
import com.android.photoalbum.viewstate.ViewState

class PhotosRepoImp(private val apiService: APIService,
                    private val appDatabase: RoomDataSource) : PhotosRepo {

    // this is for Testing purpose
    override fun getAllPhotos(): List<PhotosModel> {
        return appDatabase.photosDao().getAllAlbums()
    }

    override fun getAllAlbums(): LiveData<List<PhotosModel>> {
        return appDatabase.photosDao().getAll()
    }

    override fun getAllPhotosFromServer(stateLiveData: MutableLiveData<ViewState>) {
        stateLiveData.value = ApiCallViewState(loading = true,error = null)
        apiService.getAllPhotos().execute(
            object : RepoResponseListener<List<PhotosModel>>() {
                override fun onSuccess(response: List<PhotosModel>) {
                    if (response.isNotEmpty()) {
                        stateLiveData.value = ApiCallViewState(loading = false,error = null)
                        // update contents in database
                        updateContentsInDb(response)
                    } else {
                        stateLiveData.value = ApiCallViewState(loading = false,error = "No Data Found")
                    }
                }

                override fun onError(error: String) {
                    stateLiveData.value = ApiCallViewState(loading = false,error = error)
                }
            }
        )
    }

    private fun updateContentsInDb(response: List<PhotosModel>) {
        Thread {
            appDatabase.photosDao().updateContentsInDb(response)
        }.start()
    }
}