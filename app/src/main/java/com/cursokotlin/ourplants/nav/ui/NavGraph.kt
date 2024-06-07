package com.cursokotlin.ourplants.nav.ui

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cursokotlin.ourplants.home.ui.HomeScreen
import com.cursokotlin.ourplants.login.ui.LoginScreen
import com.cursokotlin.ourplants.nav.ui.Screen.*
import com.cursokotlin.ourplants.plans.ui.NewPlanScreen
import com.cursokotlin.ourplants.requestnewplan.ui.RequestNewPlanScreen

@Composable
fun NavGraph(
    navHostController: NavHostController = rememberNavController(),
    startDestination: String = Login.route,
    mainViewModel: MainViewModel,
    finishActivity: () -> Unit = {}
) {
    NavHost(navController = navHostController, startDestination = startDestination) {
        composable(route = Login.route) {
            BackHandler() {
                finishActivity()
            }

            LoginScreen(
                loginViewModel = mainViewModel.loginViewModel,
                onCompleteLogin = navigate(navHostController = navHostController)
            )
        }

        composable(route = Home.route) {
            HomeScreen(
                homeViewModel = mainViewModel.homeViewModel,
                interactiveDonutViewModel = mainViewModel.interactiveDonutViewModel,
                navigate = navigate(navHostController = navHostController)
            )
        }

        composable(route = NewPlan.route) {
            NewPlanScreen(navigate = navigate(navHostController = navHostController))
        }

        composable(route = RequestNewPlan.route) {
            RequestNewPlanScreen(requestNewPlanViewModel = mainViewModel.requestNewPlanViewModel)
        }
    }
}

fun navigate(navHostController: NavHostController): (String) -> Unit {
    return { screen ->
        Log.i("Login", "entra en navigate")
        navHostController.navigate(screen)
    }
}