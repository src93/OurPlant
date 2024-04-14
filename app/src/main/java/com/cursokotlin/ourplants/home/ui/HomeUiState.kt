package com.cursokotlin.ourplants.home.ui

import com.cursokotlin.ourplants.home.ui.model.KarmaPointModel

sealed interface HomeUiState {
    object Loading: HomeUiState
    data class Error(val throwable: Throwable): HomeUiState
    data class Success(val karmaPoints: List<KarmaPointModel>): HomeUiState
}