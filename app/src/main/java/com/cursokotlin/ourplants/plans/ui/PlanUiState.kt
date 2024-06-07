package com.cursokotlin.ourplants.plans.ui

import com.cursokotlin.ourplants.plans.ui.model.PlanItemListModel

sealed interface PlanUiState {
    object Loading: PlanUiState
    data class Error(val throwable: Throwable): PlanUiState
    data class Success(val listPlans: List<PlanItemListModel>): PlanUiState
}