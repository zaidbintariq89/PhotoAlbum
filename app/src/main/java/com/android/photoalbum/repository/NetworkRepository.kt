package com.android.photoalbum.repository

import com.android.photoalbum.model.AlbumsDetails
import com.android.photoalbum.model.PhotosModel
import com.android.photoalbum.repository.core.APIService
import com.android.photoalbum.repository.db.RoomDataSource
import com.android.photoalbum.repository.listener.RepoResponseListener
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class NetworkRepository(
    private val apiService: APIService,
    private val appDatabase: RoomDataSource
) {


    fun fetchUsers(repoResponseListener: RepoResponseListener<List<AlbumsDetails>>): Disposable {
        return getUsersObservables()
            .debounce(400, TimeUnit.MILLISECONDS)
            .map {
                appDatabase.photosDao().getAlbumsByGroup()
            }
            .onErrorReturn {
                emptyList()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                repoResponseListener.onSuccess(it)
            }, {
                repoResponseListener.onError(it.localizedMessage)
            })
    }

    private fun getUsersObservables(): Observable<List<PhotosModel>> {
        return Observable.merge(
            getPhotosFromDB(),
            getPhotosFromServer()
        )
    }

    private fun getPhotosFromDB(): Observable<List<PhotosModel>> {
        return appDatabase.photosDao()
            .getAllPhotosRx()
            .filter {
                it.isNotEmpty()
            }
            .toObservable()
    }

    private fun getPhotosFromServer(): Observable<List<PhotosModel>> {
        return apiService.getAllPhotosRX()
            .doOnNext {
                storeInDatabase(it)
            }
    }

    private fun storeInDatabase(list: List<PhotosModel>) {
        Completable.fromCallable {
            appDatabase.photosDao().insertAllPhotoAlbums(list)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe()
    }
}