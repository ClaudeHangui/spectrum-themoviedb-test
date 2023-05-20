package com.example.spectrum_themoviedb_test.ui.homepage.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.spectrum_themoviedb_test.ui.Destinations
import com.example.spectrum_themoviedb_test.ui.Graph
import com.example.spectrum_themoviedb_test.ui.homepage.screens.NowPlayingScreen
import com.example.spectrum_themoviedb_test.ui.homepage.screens.PopularScreen
import com.example.spectrum_themoviedb_test.ui.homepage.screens.TopRatedScreen
import com.example.spectrum_themoviedb_test.ui.homepage.screens.UpcomingScreen
import com.example.spectrum_themoviedb_test.ui.movieDetail.MovieDetailScreen
import com.example.spectrum_themoviedb_test.ui.utils.enterTransition
import com.example.spectrum_themoviedb_test.ui.utils.exitTransition
import com.example.spectrum_themoviedb_test.ui.utils.popEnterTransition
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeGraph(modifier: Modifier, navController: NavHostController) {
    AnimatedNavHost(
        navController = navController,
        route = Graph.HOME,
        modifier = modifier,
        startDestination = Destinations.NowPlayingScreen.route
    ) {
        composable(
            route = Destinations.NowPlayingScreen.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Destinations.MovieDetailScreen.route + "/{movieId}" -> {
                        enterTransition
                    }

                    Destinations.BookmarksScreen.route -> {
                        enterTransition
                    }

                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Destinations.MovieDetailScreen.route + "/{movieId}" -> {
                        exitTransition
                    }

                    Destinations.BookmarksScreen.route -> {
                        exitTransition
                    }

                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    Destinations.MovieDetailScreen.route + "/{movieId}" -> {
                        popEnterTransition
                    }

                    Destinations.BookmarksScreen.route -> {
                        popEnterTransition
                    }

                    else -> null
                }
            }
        ) {
            NowPlayingScreen(navController = navController)
        }

        composable(Destinations.PopularScreen.route) {
            PopularScreen()
        }
        composable(Destinations.TopRatedScreen.route) {
            TopRatedScreen()
        }
        composable(Destinations.UpcomingScreen.route) {
            UpcomingScreen()
        }

        detailsNavGraph(navController = navController, navGraphBuilder = this)
    }
}

@OptIn(ExperimentalAnimationApi::class)
private fun detailsNavGraph(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = Destinations.MovieDetailScreen.withArgsFormat(Destinations.MovieDetailScreen.movieId),
        arguments = listOf(
            navArgument(Destinations.MovieDetailScreen.movieId) {
                type = NavType.IntType
            }
        ),

        enterTransition = {
            when (initialState.destination.route) {
                Destinations.NowPlayingScreen.route -> {
                    enterTransition
                }

                else -> null
            }
        },
        exitTransition = {
            when (targetState.destination.route) {
                Destinations.NowPlayingScreen.route -> {
                    exitTransition
                }

                else -> null
            }
        },
        popEnterTransition = {
            when (initialState.destination.route) {
                Destinations.NowPlayingScreen.route -> {
                    popEnterTransition
                }

                else -> null
            }
        }

    ) { navBackStackEntry ->
        val args = navBackStackEntry.arguments
        MovieDetailScreen(
            movieId = args?.getInt(Destinations.MovieDetailScreen.movieId) ?: -1,
            popBackStack = { navController.popBackStack() }
        )
    }
}