package com.android.photoalbum

import com.android.photoalbum.model.PhotosModel
import com.android.photoalbum.repository.PhotosRepo
import com.android.photoalbum.repository.PhotosRepoImp
import com.android.photoalbum.repository.core.APIService
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Call

@RunWith(MockitoJUnitRunner::class)
class PhotosRepositoryTest {

    @Mock
    private lateinit var apiService: APIService

    private lateinit var photosRepo: PhotosRepo

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        photosRepo = PhotosRepoImp(apiService)
    }

    @Test
    fun testPhotosResponse_whenRequested() {
        val photoModel = PhotosModel(albumId = 1,id = 1,title = "Test",url = "test",thumbnailUrl = "test")
        val response = listOf(photoModel)

        val call: Call<List<PhotosModel>> = apiService.getAllPhotos()
        Mockito.`when`(apiService.getAllPhotos()).thenReturn(call)
    }
}
