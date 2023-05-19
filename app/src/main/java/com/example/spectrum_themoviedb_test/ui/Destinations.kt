package com.example.spectrum_themoviedb_test.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
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
        icon = Icons.Default.Favorite
    )
    object TopRatedScreen : Destinations(
        route = "top_rated_screen",
        title = "TopRated",
        icon = Icons.Default.Star
    )
    object UpcomingScreen : Destinations(
        route = "upcoming_screen",
        title = "Upcoming",
        icon = Icons.Default.DateRange
    )

    object MovieDetailScreen : Destinations("movie_detail_screen"){
        val movieId = "movieId"
    }
    object BookmarksScreen : Destinations("bookmarks_screen")

    fun withArgsFormat(vararg args: String) : String {
        return buildString {
            append(route)
            args.forEach{ arg ->
                append("/{$arg}")
            }
        }
    }
}
