package com.cursokotlin.ourplants.requestnewplan.data.network

import android.util.Log
import com.cursokotlin.ourplants.requestnewplan.data.network.clients.UnplashApiClient
import com.cursokotlin.ourplants.requestnewplan.ui.model.ItemResult
import com.cursokotlin.ourplants.requestnewplan.ui.model.ResponseGetPhotosUI
import com.cursokotlin.ourplants.requestnewplan.ui.model.Urls
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetPhotoService @Inject constructor(private val unplashApiClient: UnplashApiClient) {
    suspend fun getPhoto(page: Int, query: String): ResponseGetPhotosUI {
        return withContext(Dispatchers.IO) {
            var itemResult: List<ItemResult> = listOf();
            unplashApiClient.getPhotos(page = page, query = query).onSuccess {
                Log.i("getPhoto", "Response: ${it.totalPages}")
                itemResult = it.results.map { photo ->
                    ItemResult(
                        urls = Urls(
                            full = photo.urls.full,
                            regular = photo.urls.regular,
                            small = photo.urls.small
                        )
                    )
                }
            }.onFailure {
                Log.i("getPhoto", "Error ${it.localizedMessage}")
            }

            return@withContext ResponseGetPhotosUI(photos = itemResult)
        }
    }
}