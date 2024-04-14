package com.cursokotlin.ourplants.home.domain

import com.cursokotlin.ourplants.home.data.KarmaPointRepository
import com.cursokotlin.ourplants.home.ui.model.KarmaPointModel
import javax.inject.Inject

class UpdateKarmaPointsUseCase @Inject constructor(private val karmaPointRepository: KarmaPointRepository) {
    suspend operator fun invoke(karmaPointModel: KarmaPointModel) {
        karmaPointRepository.updateKarmaPoints(karmaPointModel = karmaPointModel)
    }
}