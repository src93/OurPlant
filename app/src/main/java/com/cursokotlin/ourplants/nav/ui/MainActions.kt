package com.cursokotlin.ourplants.nav.ui

import android.util.Log
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
        Log.i("Pruebas_barNavigation", "entra en el main action")
        navController.navigate(NewPlan.route)
    }
    val  goCheckPlan = {
        navController.navigate(CheckPlan.route)
    }
}