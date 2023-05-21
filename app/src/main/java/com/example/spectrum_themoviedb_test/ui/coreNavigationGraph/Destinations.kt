package com.example.spectrum_themoviedb_test.ui.coreNavigationGraph

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destinations(
    val route: String,
    val title: String? = null,
    val icon: ImageVector? = null,
) {
    object NowPlayingScreen : Destinations(
        route = "now_playing_screen",
        title = "NowPlaying",
        icon = Icons.Default.Home
    )
    object PopularScreen : Destinations(
        route = "popular_screen",
        title = "Popular",
        icon = Icons.Default.ThumbUp
    )
    object TopRatedScreen : Destinations(
        route = "top_rated_screen",
        title = "TopRated",
        icon = Icons.Default.Star
    )
    object UpcomingScreen : Destinations(
        route = "upcoming_screen",
        title = "Upcoming",
        icon = Icons.Default.Refresh
    )

    object MovieDetailScreen : Destinations("movie_detail_screen"){
        val movieId = "movieId"
    }
    object FavoriteMoviesScreen : Destinations("bookmarks_screen")

    object SearchScreen : Destinations("search_screen")

    object HomeScreen : Destinations("home_screen")

    fun withArgsFormat(vararg args: String) : String {
        return buildString {
            append(route)
            args.forEach{ arg ->
                append("/{$arg}")
            }
        }
    }
}
