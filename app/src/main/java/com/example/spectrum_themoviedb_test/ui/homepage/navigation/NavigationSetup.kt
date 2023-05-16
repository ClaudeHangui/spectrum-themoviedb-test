package com.example.spectrum_themoviedb_test.ui.homepage.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.spectrum_themoviedb_test.ui.homepage.screens.NowPlayingScreen
import com.example.spectrum_themoviedb_test.ui.homepage.screens.PopularScreen
import com.example.spectrum_themoviedb_test.ui.homepage.screens.TopRatedScreen
import com.example.spectrum_themoviedb_test.ui.homepage.screens.UpcomingScreen

@Composable
fun NavigationSetup(modifier: Modifier, navController: NavHostController) {
    NavHost(navController, startDestination = NavRoute.Home.path, modifier) {
        composable(NavRoute.Home.path) {
            NowPlayingScreen()
        }
        composable(NavRoute.Popular.path) {
            PopularScreen()
        }
        composable(NavRoute.Top.path) {
            TopRatedScreen()
        }
        composable(NavRoute.Upcoming.path) {
            UpcomingScreen()
        }
    }
}

private fun addHomeScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
){
    navGraphBuilder.composable(route = NavRoute.Home.path) {

    }
}