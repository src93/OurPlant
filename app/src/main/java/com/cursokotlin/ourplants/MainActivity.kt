package com.cursokotlin.ourplants

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.cursokotlin.ourplants.home.ui.HomeViewModel
import com.cursokotlin.ourplants.interactivedonut.ui.InteractiveDonutViewModel
import com.cursokotlin.ourplants.login.ui.LoginViewModel
import com.cursokotlin.ourplants.nav.ui.MainViewModel
import com.cursokotlin.ourplants.nav.ui.NavGraph
import com.cursokotlin.ourplants.ui.theme.OurPlantsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    private val interactiveDonutViewModel: InteractiveDonutViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OurPlantsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val mainViewModel = MainViewModel(
                        loginViewModel = loginViewModel,
                        homeViewModel = homeViewModel,
                        interactiveDonutViewModel = interactiveDonutViewModel
                    )
                    NavGraph(mainViewModel = mainViewModel, finishActivity = { finish() })
                }
            }
        }
    }
}