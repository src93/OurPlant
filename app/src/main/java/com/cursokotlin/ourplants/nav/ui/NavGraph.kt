package com.cursokotlin.ourplants.nav.ui

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cursokotlin.ourplants.home.ui.HomeScreen
import com.cursokotlin.ourplants.login.ui.LoginScreen
import com.cursokotlin.ourplants.nav.ui.Screen.*

@Composable
fun NavGraph(
    navHostController: NavHostController = rememberNavController(),
    startDestination: String = Login.route,
    mainViewModel: MainViewModel,
    finishActivity: () -> Unit = {}
) {
    val actions = remember(navHostController) {
        MainActions(navController = navHostController)
    }

    NavHost(navController = navHostController, startDestination = startDestination) {
        composable(route = Login.route) {
            BackHandler() {
                finishActivity()
            }

            LoginScreen(loginViewModel = mainViewModel.loginViewModel) { actions.goHome() }
        }

        composable(route = Home.route) {
            // aqui tengo que pasar los 3 navegaciones
            HomeScreen(homeViewModel = mainViewModel.homeViewModel, interactiveDonutViewModel = mainViewModel.interactiveDonutViewModel)
        }
    }
}