package com.android.photoalbum.repository

import androidx.lifecycle.MutableLiveData
import com.android.photoalbum.model.AlbumsDetails
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

    override fun getAllAlbums(albumsLV: MutableLiveData<List<AlbumsDetails>>) {
        Thread {
            val list = appDatabase.photosDao().getAlbumsByGroup()
            albumsLV.postValue(list)
        }.start()
    }

    override fun getAllPhotosFromServer(stateLiveData: MutableLiveData<ViewState>) {
        stateLiveData.value = ApiCallViewState(loading = true, error = null, dataFound = false)
        apiService.getAllPhotos().execute(
                object : RepoResponseListener<List<PhotosModel>>() {
                    override fun onSuccess(response: List<PhotosModel>) {
                        if (response.isNotEmpty()) {
                            stateLiveData.value = ApiCallViewState(loading = false, error = null, dataFound = true)
                            // update contents in database
                            updateContentsInDb(response)
                        } else {
                            stateLiveData.value = ApiCallViewState(loading = false, error = "No Data Found", dataFound = false)
                        }
                    }

                    override fun onError(error: String) {
                        stateLiveData.value = ApiCallViewState(loading = false, error = error, dataFound = false)
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