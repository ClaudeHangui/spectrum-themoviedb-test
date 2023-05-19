package com.example.spectrum_themoviedb_test.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.spectrum_themoviedb_test.ui.homepage.screens.HomeScreen
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RootNavigationGraph(
    navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        route = Graph.ROOT,
        startDestination = Graph.HOME
    ) {
        composable(Graph.HOME) {
            val navBarNavController = rememberAnimatedNavController()
            HomeScreen(navController = navBarNavController)
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val HOME = "main_graph"
    const val MOVIE_DETAILS = "movie_details_graph"
}