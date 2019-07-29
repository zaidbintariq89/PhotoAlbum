package com.android.photoalbum.repository

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

    override fun getAllAlbums(forceRefresh: Boolean,stateLiveData: MutableLiveData<ViewState>) {
        if (!forceRefresh) {
            Thread {
                val list = appDatabase.photosDao().getAlbumsByGroup()
                if (list.isEmpty()) {
                    fetchFromServer(stateLiveData)
                } else {
                    stateLiveData.postValue(ApiCallViewState(loading = false, error = null, data = list))
                }
            }.start()
        } else {
            fetchFromServer(stateLiveData)
        }
    }

    private fun fetchFromServer(stateLiveData: MutableLiveData<ViewState>) {
        stateLiveData.postValue(ApiCallViewState(loading = true, error = null, data = null))
        apiService.getAllPhotos().execute(
            object : RepoResponseListener<List<PhotosModel>>() {
                override fun onSuccess(response: List<PhotosModel>) {
                    if (response.isNotEmpty()) {
                        // update contents in database
                        insertIntoDb(response,stateLiveData)
                    } else {
                        stateLiveData.postValue(ApiCallViewState(loading = false, error = "No Data Found", data = null))
                    }
                }

                override fun onError(error: String) {
                    stateLiveData.value = ApiCallViewState(loading = false, error = error, data = null)
                }
            }
        )
    }

    private fun insertIntoDb(response: List<PhotosModel>, stateLiveData: MutableLiveData<ViewState>) {
        Thread {
            appDatabase.photosDao().updateContentsInDb(response)
            // load saved data from Db and post to view
            val list = appDatabase.photosDao().getAlbumsByGroup()
            stateLiveData.postValue(ApiCallViewState(loading = false, error = "No Data Found", data = list))
        }.start()
    }
}