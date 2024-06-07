package com.cursokotlin.ourplants.requestnewplan.domain

import com.cursokotlin.ourplants.requestnewplan.data.GetPhotoRepository
import com.cursokotlin.ourplants.requestnewplan.ui.model.ResponseGetPhotosUI
import javax.inject.Inject

class GetPhotoUseCase @Inject constructor(private val getPhotoRepository: GetPhotoRepository) {
    suspend operator fun invoke(page: Int, query: String): ResponseGetPhotosUI {
        return getPhotoRepository.getPhoto(page = page, query = query)
    }
}