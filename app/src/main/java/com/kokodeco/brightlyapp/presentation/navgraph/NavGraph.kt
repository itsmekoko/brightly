package com.kokodeco.brightlyapp.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.kokodeco.brightlyapp.presentation.newsNavigator.NewsNavigator
import com.kokodeco.brightlyapp.presentation.onboarding.IntroDemoScreen
import com.kokodeco.brightlyapp.presentation.onboarding.IntroDemoViewModel

@Composable
fun NavGraph(
    startDestination: String
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        navigation(
            route = NavScreen.AppStartNavigation.navScreen,
            startDestination = NavScreen.IntroDemoScreen.navScreen
        ) {
            composable(
                NavScreen.IntroDemoScreen.navScreen
            ) {
                val viewModel: IntroDemoViewModel = hiltViewModel()
                IntroDemoScreen(
                    onEvent = viewModel::onEvent
                )
            }
        }
        navigation(
            route = NavScreen.NewsNavigation.navScreen,
            startDestination = NavScreen.NewsNavigatorScreen.navScreen
        ) {
            composable(route = NavScreen.NewsNavigatorScreen.navScreen) {
                NewsNavigator()
            }
        }
    }
}
