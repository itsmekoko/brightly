package com.kokodeco.brightlyapp.presentation.newsNavigator

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kokodeco.brightlyapp.R
import com.kokodeco.brightlyapp.domain.model.Article
import com.kokodeco.brightlyapp.presentation.bookmark.BookmarkScreen
import com.kokodeco.brightlyapp.presentation.bookmark.BookmarkViewModel
import com.kokodeco.brightlyapp.presentation.details.DetailsScreen
import com.kokodeco.brightlyapp.presentation.details.DetailsViewModel
import com.kokodeco.brightlyapp.presentation.home.HomeScreen
import com.kokodeco.brightlyapp.presentation.home.HomeViewModel
import com.kokodeco.brightlyapp.presentation.navgraph.NavScreen
import com.kokodeco.brightlyapp.presentation.newsNavigator.components.BottomNavigationItem
import com.kokodeco.brightlyapp.presentation.newsNavigator.components.NewsBottomNavigation
import com.kokodeco.brightlyapp.presentation.search.SearchScreen
import com.kokodeco.brightlyapp.presentation.search.SearchViewModel

@Composable
fun NewsNavigator() {
    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.ic_home, text = "Home"),
            BottomNavigationItem(icon = R.drawable.ic_search, text = "Search"),
            BottomNavigationItem(icon = R.drawable.ic_bookmark, text = "Bookmark")
        )
    }

    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }
    selectedItem = when (backStackState?.destination?.route) {
        NavScreen.HomeScreen.navScreen -> 0
        NavScreen.SearchScreen.navScreen -> 1
        NavScreen.BookmarkScreen.navScreen -> 2
        else -> 0
    }

    // Hide the bottom navigation when the user is in the details screen
    val isBottomBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == NavScreen.HomeScreen.navScreen ||
                backStackState?.destination?.route == NavScreen.SearchScreen.navScreen ||
                backStackState?.destination?.route == NavScreen.BookmarkScreen.navScreen
    }

    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        if (isBottomBarVisible) {
            NewsBottomNavigation(
                items = bottomNavigationItems,
                selectedItem = selectedItem,
                onItemClick = { index ->
                    when (index) {
                        0 -> navigateToTab(
                            navController = navController,
                            route = NavScreen.HomeScreen.navScreen
                        )
                        1 -> navigateToTab(
                            navController = navController,
                            route = NavScreen.SearchScreen.navScreen
                        )
                        2 -> navigateToTab(
                            navController = navController,
                            route = NavScreen.BookmarkScreen.navScreen
                        )
                    }
                }
            )
        }
    }) {
        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = NavScreen.HomeScreen.navScreen,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable(route = NavScreen.HomeScreen.navScreen) {
                val homeViewModel: HomeViewModel = hiltViewModel()
                HomeScreen(
                    viewModel = homeViewModel,
                    navigateToDetails = { article ->
                        navigateToDetails(navController, article)
                    }
                )
            }

            composable(route = NavScreen.SearchScreen.navScreen) {
                val searchViewModel: SearchViewModel = hiltViewModel()
                val state = searchViewModel.state.value
                OnBackClickStateSaver(navController = navController)
                SearchScreen(
                    state = state,
                    event = searchViewModel::onEvent,
                    navigateToDetails = { article ->
                        navigateToDetails(
                            navController = navController,
                            article = article
                        )
                    }
                )
            }
            composable(route = NavScreen.DetailsScreen.navScreen) {
                val detailsViewModel: DetailsViewModel = hiltViewModel()
                navController.previousBackStackEntry?.savedStateHandle?.get<Article?>("article")
                    ?.let { article ->
                        DetailsScreen(
                            article = article,
                            event = detailsViewModel::onEvent,
                            navigateUp = { navController.navigateUp() },
                            sideEffect = detailsViewModel.sideEffect
                        )
                    }
            }
            composable(route = NavScreen.BookmarkScreen.navScreen) {
                val bookmarkViewModel: BookmarkViewModel = hiltViewModel()
                val state = bookmarkViewModel.state.value
                OnBackClickStateSaver(navController = navController)
                BookmarkScreen(
                    state = state,
                    navigateToDetails = { article ->
                        navigateToDetails(
                            navController = navController,
                            article = article
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun OnBackClickStateSaver(navController: NavController) {
    BackHandler(true) {
        navigateToTab(
            navController = navController,
            route = NavScreen.HomeScreen.navScreen
        )
    }
}

private fun navigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { screenRoute ->
            popUpTo(screenRoute) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}

private fun navigateToDetails(navController: NavController, article: Article) {
    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
    navController.navigate(
        route = NavScreen.DetailsScreen.navScreen
    )
}
