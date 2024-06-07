package com.cursokotlin.ourplants.requestnewplan.domain

import com.cursokotlin.ourplants.plans.data.PlanRepository
import com.cursokotlin.ourplants.plans.ui.model.PlanItemListModel
import javax.inject.Inject

class AddPlan @Inject constructor(private val planRepository: PlanRepository) {
    suspend operator fun invoke(planItemListModel: PlanItemListModel) {
        planRepository.addPlan(planItemListModel = planItemListModel)
    }
}