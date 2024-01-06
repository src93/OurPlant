package com.cursokotlin.ourplants.nav.ui

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

/**
 * Models the navigation actions in the app.
 */
class MainActions(navController: NavController) {
    val goHome = { ->
        navController.navigate(MainDestinations.HOME_ROUTE)
    }
}