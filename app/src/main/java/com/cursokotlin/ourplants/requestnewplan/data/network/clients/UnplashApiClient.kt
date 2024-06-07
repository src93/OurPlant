package com.cursokotlin.ourplants.requestnewplan.data.network.clients

import com.cursokotlin.ourplants.requestnewplan.data.network.response.GetPhotoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UnplashApiClient {
    @GET("/search/photos")
    suspend fun getPhotos(@Query("page") page: Int, @Query("query") query: String): Result<GetPhotoResponse>
}