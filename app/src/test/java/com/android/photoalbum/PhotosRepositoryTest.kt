package com.android.photoalbum

import com.android.photoalbum.model.PhotosModel
import com.android.photoalbum.repository.PhotosRepo
import com.android.photoalbum.repository.PhotosRepoImp
import com.android.photoalbum.repository.core.APIService
import com.android.photoalbum.repository.db.PhotosDao
import com.android.photoalbum.repository.db.RoomDataSource
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PhotosRepositoryTest {

    @Mock
    private lateinit var apiService: APIService
    @Mock
    private lateinit var roomDataSource: RoomDataSource
    @Mock
    private lateinit var albumsDao: PhotosDao

    private lateinit var photosRepo: PhotosRepo

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        photosRepo = PhotosRepoImp(apiService,roomDataSource)
        Mockito.`when`(roomDataSource.photosDao()).thenReturn(albumsDao)
    }

    @Test
    fun testNoAlbumsReturned_whenNoAlbumsEnter() {
        // Given that the RoomDataSource returns an empty list of albums
        Assert.assertEquals(emptyList<PhotosModel>(),roomDataSource.photosDao().getAllAlbums())
    }

    @Test
    fun testAlbumReturned_whenAlbumSaved() {
        // save mock object
        val album = PhotosModel(uid = 1,id = 1,albumId = 1,title = "testAlbum",thumbnailUrl = "http://google.com")
        Mockito.`when`(roomDataSource.photosDao().getAllAlbums())
            .thenReturn(listOf(album))

        // get albums
        val albumsFromDb = roomDataSource.photosDao().getAllAlbums()

        // Given that the RoomDataSource returns saved album
        Assert.assertEquals(listOf(album),albumsFromDb)
    }
}
