package com.cursokotlin.ourplants.requestnewplan.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cursokotlin.ourplants.plans.ui.model.PlanItemListModel
import com.cursokotlin.ourplants.requestnewplan.domain.AddPlan
import com.cursokotlin.ourplants.requestnewplan.domain.GetPhotoUseCase
import com.cursokotlin.ourplants.requestnewplan.ui.model.ItemResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestNewPlanViewModel @Inject constructor(
    private val getPhotoUseCase: GetPhotoUseCase,
    private val addPlan: AddPlan
) : ViewModel() {
    private var _switchStateActivity = MutableLiveData<Boolean>(false)
    private var _newActivity = MutableLiveData("")
    private var _numStars = MutableLiveData(1)
    private var _expandedListActivities = MutableLiveData(false)
    private var _selectedActivity = MutableLiveData("")
    private var _description = MutableLiveData("")
    private var _queryToSearch = MutableLiveData("")
    private var _listPhotos = MutableLiveData<List<ItemResult>>(listOf())
    private var _selectedPhoto = MutableLiveData(-1)
    private var _urlPhoto = MutableLiveData("")
    val switchStateActivity
        get() = _switchStateActivity
    val newActivity
        get() = _newActivity
    val numStars
        get() = _numStars
    val expandedListActivities
        get() = _expandedListActivities
    val selectedActivity
        get() = _selectedActivity
    val description
        get() = _description
    val queryToSearch
        get() = _queryToSearch
    val listPhotos
        get() = _listPhotos
    val selectedPhoto
        get() = _selectedPhoto
    val urlPhoto
        get() = _urlPhoto

    init {
        viewModelScope.launch {
//            getPhoto()
        }
    }

    suspend fun getPhoto() {
        Log.i("INIT_request", "entra en el Init")
        getPhotoUseCase(page = 1, query = "Salamanca")
    }

    fun changeStateActivity() {
        _switchStateActivity.value = !_switchStateActivity.value!!
    }

    fun handleOnChangeNewActivityField(newActivity: String) {
        _newActivity.value = newActivity
    }

    fun onClickOtter(numStar: Int) {
        _numStars.value = numStar
    }

    fun onChangeExpandedListMenu(state: Boolean) {
        _expandedListActivities.value = state
    }

    fun onSelectedActivity(selectedActivity: String) {
        _selectedActivity.value = selectedActivity
        _expandedListActivities.value = false
    }

    fun onDescriptionChange(description: String) {
        _description.value = description
    }

    fun searchNewPhotos() {
        viewModelScope.launch {
            val responseGetPhotosUI = getPhotoUseCase(page = 1, query = _queryToSearch.value!!)
            _listPhotos.value = responseGetPhotosUI.photos
        }
    }

    fun onValueChangeSearchInput(query: String) {
        _queryToSearch.value = query
    }

    fun onClickPhoto(url: String, index: Int) {
        _urlPhoto.value = url
        _selectedPhoto.value = index
    }

    fun registerNewPlan() {
        viewModelScope.launch {
            addPlan(
                planItemListModel = PlanItemListModel(
                    plan = _newActivity.value!!,
                    stars = _numStars.value!!,
                    description = _description.value!!,
                    image = _urlPhoto.value!!
                )
            )
        }
    }
}