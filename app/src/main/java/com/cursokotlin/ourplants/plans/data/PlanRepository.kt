package com.cursokotlin.ourplants.plans.data

import com.cursokotlin.ourplants.plans.ui.model.PlanItemListModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlanRepository @Inject constructor(private val planDao: PlanDao) {
    val getPlans: Flow<List<PlanItemListModel>> = planDao.getActivities().map { plans ->
        plans.map {
            PlanItemListModel(plan = it.plan, image = it.image, description = it.description, stars = it.stars)
        }
    }

    suspend fun addPlan(planItemListModel: PlanItemListModel) {
        planDao.addPlan(PlanEntity(plan = planItemListModel.plan, image = planItemListModel.image, description = planItemListModel.description, stars = planItemListModel.stars))
    }
}