package com.kokodeco.brightlyapp.presentation.navgraph

sealed class NavScreen(
    val navScreen: String
) {

    object IntroDemoScreen : NavScreen(navScreen = "IntroDemoScreen")
    object HomeScreen : NavScreen(navScreen = "homeScreen")
    object SearchScreen : NavScreen(navScreen = "searchScreen")
    object BookmarkScreen : NavScreen(navScreen = "bookmarkScreen")
    object DetailsScreen : NavScreen(navScreen = "detailsScreen")
    object AppStartNavigation : NavScreen(navScreen = "appStartNavigation")
    object NewsNavigation : NavScreen(navScreen = "newsNavigation")
    object NewsNavigatorScreen : NavScreen(navScreen = "newsNavigator")
}
