package com.cursokotlin.ourplants.nav.ui

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Login : Screen("login")
    object NewPlan : Screen("new_plan")
    object CheckPlan : Screen("check_plan")
}