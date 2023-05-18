package com.example.spectrum_themoviedb_test.ui.homepage.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.spectrum_themoviedb_test.ui.homepage.screens.NowPlayingScreen
import com.example.spectrum_themoviedb_test.ui.homepage.screens.PopularScreen
import com.example.spectrum_themoviedb_test.ui.homepage.screens.TopRatedScreen
import com.example.spectrum_themoviedb_test.ui.homepage.screens.UpcomingScreen
import com.example.spectrum_themoviedb_test.ui.movieDetail.MovieDetailScreen

@Composable
fun NavigationSetup(modifier: Modifier, navController: NavHostController) {
    NavHost(navController, startDestination = NavRoute.Home.path, modifier) {
        composable(NavRoute.Home.path) {
            NowPlayingScreen(
                navigateMovieDetail = { movieId ->
                    navController.navigate(NavRoute.MovieDetail.withArgs(movieId.toString()))
                },
                popBackStack = { navController.popBackStack() }
                )
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

        addMovieDetailScreen(navController, this)
    }
}

private fun addMovieDetailScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
){
    navGraphBuilder.composable(route = NavRoute.MovieDetail.withArgsFormat(NavRoute.MovieDetail.id),
        arguments = listOf(
            navArgument(NavRoute.MovieDetail.id) {
                type = NavType.IntType
            }
        )
    ) { navBackStackEntry ->
        val args = navBackStackEntry.arguments
        MovieDetailScreen(
            movieId = args?.getInt(NavRoute.MovieDetail.id) ?: 0,
            popBackStack = { navController.popBackStack() }
        )

    }
}