package com.cursokotlin.ourplants.nav.ui

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Login : Screen("login")
    object NEW_PLAN : Screen("new_plan")
    object CHECK_PLAN : Screen("check_plan")
}