package com.cursokotlin.ourplants.home.domain

import com.cursokotlin.ourplants.home.data.KarmaPointRepository
import com.cursokotlin.ourplants.home.ui.model.KarmaPointModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetKarmaPointsUseCase @Inject constructor(private val karmaPointRepository: KarmaPointRepository) {
    operator fun invoke(): Flow<List<KarmaPointModel>> {
        return karmaPointRepository.getKarmaPoints
    }
}