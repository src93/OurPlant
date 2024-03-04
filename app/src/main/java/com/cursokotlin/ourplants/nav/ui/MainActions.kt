package com.cursokotlin.ourplants.nav.ui

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.cursokotlin.ourplants.nav.ui.Screen.*

/**
 * Models the navigation actions in the app.
 */
class MainActions(navController: NavController) {
    val goHome = { ->
        navController.navigate(Home.route)
    }
    val goNewPlan = {
        navController.navigate(NEW_PLAN.route)
    }
    val  goCheckPlan = {
        navController.navigate(CHECK_PLAN.route)
    }
}