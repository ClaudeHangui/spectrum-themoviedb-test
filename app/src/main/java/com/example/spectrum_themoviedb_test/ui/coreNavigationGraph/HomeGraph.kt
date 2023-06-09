package com.example.spectrum_themoviedb_test.ui.coreNavigationGraph

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.spectrum_themoviedb_test.ui.bookmarkedMovies.FavoritesMoviesScreen
import com.example.spectrum_themoviedb_test.ui.homepage.screens.HomeScreen
import com.example.spectrum_themoviedb_test.ui.homepage.screens.NowPlayingScreen
import com.example.spectrum_themoviedb_test.ui.homepage.screens.PopularScreen
import com.example.spectrum_themoviedb_test.ui.homepage.screens.TopRatedScreen
import com.example.spectrum_themoviedb_test.ui.homepage.screens.UpcomingScreen
import com.example.spectrum_themoviedb_test.ui.movieDetail.MovieDetailScreen
import com.example.spectrum_themoviedb_test.ui.searchMovie.SearchMovieScreen
import com.example.spectrum_themoviedb_test.ui.utils.enterTransition
import com.example.spectrum_themoviedb_test.ui.utils.exitTransition
import com.example.spectrum_themoviedb_test.ui.utils.popEnterTransition
import com.example.spectrum_themoviedb_test.ui.utils.popExitTransition
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
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Destinations.MovieDetailScreen.route + "/{movieId}" -> {
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
                    else -> null
                }
            }

        ) {
            NowPlayingScreen(navController = navController)
        }

        composable(
            route = Destinations.PopularScreen.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Destinations.MovieDetailScreen.route + "/{movieId}" -> {
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
                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    Destinations.MovieDetailScreen.route + "/{movieId}" -> {
                        popEnterTransition
                    }
                    else -> null
                }
            }
        ) {
            PopularScreen(navController = navController)
        }
        composable(
            route = Destinations.TopRatedScreen.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Destinations.MovieDetailScreen.route + "/{movieId}" -> {
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
                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    Destinations.MovieDetailScreen.route + "/{movieId}" -> {
                        popEnterTransition
                    }
                    else -> null
                }
            }
        ) {
            TopRatedScreen(navController = navController)
        }
        composable(
            route = Destinations.UpcomingScreen.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Destinations.MovieDetailScreen.route + "/{movieId}" -> {
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
                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    Destinations.MovieDetailScreen.route + "/{movieId}" -> {
                        popEnterTransition
                    }
                    else -> null
                }
            }
        ) {
            UpcomingScreen(navController = navController)
        }

        detailsNavGraph(navController = navController, navGraphBuilder = this)

        composable(
            route = Destinations.SearchScreen.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Destinations.MovieDetailScreen.route + "/{movieId}" -> {
                        enterTransition
                    }
                    Destinations.HomeScreen.route -> {
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
                    Destinations.HomeScreen.route -> {
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
                    else -> null
                }
            },
            popExitTransition = {
                when (initialState.destination.route) {
                    Destinations.HomeScreen.route -> {
                        popExitTransition
                    }
                    else -> null
                }
            }
        ) {
            SearchMovieScreen(
                navigateBack = { navController.popBackStack() },
                navigateMovieDetail = { movieId ->
                    navController.navigate(Destinations.MovieDetailScreen.route + "/${movieId}")
                }
            )
        }


        composable(
            route = Destinations.FavoriteMoviesScreen.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Destinations.MovieDetailScreen.route + "/{movieId}" -> {
                        enterTransition
                    }
                    Destinations.HomeScreen.route -> {
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
                    Destinations.HomeScreen.route -> {
                        exitTransition
                    }
                    else -> null
                }
            },
            popExitTransition = {
                when (initialState.destination.route) {
                    Destinations.MovieDetailScreen.route + "/{movieId}" -> {
                        popExitTransition
                    }
                    Destinations.HomeScreen.route -> {
                        popExitTransition
                    }
                    else -> null
                }
            }

        ) {
            FavoritesMoviesScreen(
                navigateBack = { navController.popBackStack() },
                navigateMovieDetail = { movieId ->
                    navController.navigate(Destinations.MovieDetailScreen.route + "/${movieId}")
                }
            )
        }

        composable(
            route = Destinations.HomeScreen.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Destinations.SearchScreen.route -> {
                        enterTransition
                    }
                    Destinations.FavoriteMoviesScreen.route -> {
                        enterTransition
                    }
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Destinations.SearchScreen.route -> {
                        exitTransition
                    }
                    Destinations.FavoriteMoviesScreen.route -> {
                        exitTransition
                    }
                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    Destinations.SearchScreen.route -> {
                        popEnterTransition
                    }
                    Destinations.FavoriteMoviesScreen.route -> {
                        popEnterTransition
                    }
                    else -> null
                }
            },
        ){
            HomeScreen()
        }
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
                Destinations.SearchScreen.route -> {
                    enterTransition
                }
                Destinations.FavoriteMoviesScreen.route -> {
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
                Destinations.SearchScreen.route -> {
                    exitTransition
                }
                Destinations.FavoriteMoviesScreen.route -> {
                    exitTransition
                }

                else -> null
            }
        },
        popExitTransition = {
            when (targetState.destination.route) {
                Destinations.NowPlayingScreen.route -> {
                    popExitTransition
                }
                Destinations.SearchScreen.route -> {
                    popExitTransition
                }
                Destinations.FavoriteMoviesScreen.route -> {
                    popExitTransition
                }

                else -> null
            }
        },

    ) { navBackStackEntry ->
        val args = navBackStackEntry.arguments
        MovieDetailScreen(
            movieId = args?.getInt(Destinations.MovieDetailScreen.movieId) ?: -1,
            popBackStack = { navController.popBackStack() }
        )
    }
}