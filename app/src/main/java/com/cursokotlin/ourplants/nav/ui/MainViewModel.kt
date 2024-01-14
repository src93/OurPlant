package com.cursokotlin.ourplants.nav.ui

import com.cursokotlin.ourplants.home.ui.HomeViewModel
import com.cursokotlin.ourplants.interactivedonut.ui.InteractiveDonutViewModel
import com.cursokotlin.ourplants.login.ui.LoginViewModel

data class MainViewModel(
    val loginViewModel: LoginViewModel,
    val homeViewModel: HomeViewModel,
    val interactiveDonutViewModel: InteractiveDonutViewModel
)
