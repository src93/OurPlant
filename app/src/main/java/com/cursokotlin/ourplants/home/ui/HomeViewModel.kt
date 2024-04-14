package com.cursokotlin.ourplants.home.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cursokotlin.ourplants.home.domain.GetKarmaPointsUseCase
import com.cursokotlin.ourplants.home.domain.UpdateKarmaPointsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import com.cursokotlin.ourplants.home.ui.HomeUiState.*
import com.cursokotlin.ourplants.home.ui.model.KarmaPointModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(getKarmaPointsUseCase: GetKarmaPointsUseCase, private val updateKarmaPointsUseCase: UpdateKarmaPointsUseCase): ViewModel() {
    val uiState: StateFlow<HomeUiState> = getKarmaPointsUseCase().map(::Success).catch { Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)
    private val _showModal = MutableLiveData<Boolean>(false)
    val showModal
        get() = _showModal

    suspend fun updateKarmaPoints(username: String, karmaPoints: Int) {
        updateKarmaPointsUseCase(KarmaPointModel(username = username, karmaPoints = karmaPoints))
        _showModal.value = false
    }

    fun showViewModal() {
        _showModal.value = true
    }
}