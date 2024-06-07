package com.cursokotlin.ourplants.plans.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cursokotlin.ourplants.plans.domain.GetPlans
import com.cursokotlin.ourplants.plans.ui.PlanUiState.*
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class PlanViewModal @Inject constructor(private val getPlans: GetPlans) : ViewModel() {
    val uiState: StateFlow<PlanUiState> =
        getPlans().map(::Success).catch { Error(it) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)
}