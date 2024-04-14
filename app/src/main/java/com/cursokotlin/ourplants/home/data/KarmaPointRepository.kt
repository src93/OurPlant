package com.cursokotlin.ourplants.home.data

import com.cursokotlin.ourplants.home.ui.model.KarmaPointModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class KarmaPointRepository @Inject constructor(private val karmaPointDao: KarmaPointDao) {
    val getKarmaPoints: Flow<List<KarmaPointModel>> = karmaPointDao.getKarmaPoints().map { users ->
        users.map {
            KarmaPointModel(username = it.username, karmaPoints = it.karmaPoints)
        }
    }

    suspend fun updateKarmaPoints(karmaPointModel: KarmaPointModel) {
        karmaPointDao.updateKarmaPoints(KarmaPointEntity(username = karmaPointModel.username, karmaPoints = karmaPointModel.karmaPoints))
    }
}