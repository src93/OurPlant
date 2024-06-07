package com.cursokotlin.ourplants.requestnewplan.data

import com.cursokotlin.ourplants.requestnewplan.data.network.GetPhotoService
import com.cursokotlin.ourplants.requestnewplan.ui.model.ResponseGetPhotosUI
import javax.inject.Inject

class GetPhotoRepository @Inject constructor(private val getPhotoService: GetPhotoService) {
    suspend fun getPhoto(page: Int, query: String): ResponseGetPhotosUI {
        return getPhotoService.getPhoto(page = page, query = query)
    }
}