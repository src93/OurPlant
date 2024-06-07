package com.cursokotlin.ourplants.plans.domain

import com.cursokotlin.ourplants.plans.data.PlanRepository
import com.cursokotlin.ourplants.plans.ui.model.PlanItemListModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlans @Inject constructor(private val planRepository: PlanRepository) {
    operator fun invoke(): Flow<List<PlanItemListModel>> {
        return planRepository.getPlans
    }
}