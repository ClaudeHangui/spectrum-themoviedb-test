package com.example.spectrum_themoviedb_test.ui.homepage.navigation

sealed class NavRoute (val path: String) {
    object Home: NavRoute("now_playing")
    object Popular: NavRoute("popular")
    object Top: NavRoute("top_rated")
    object Upcoming: NavRoute("upcoming")

    // build navigation path (for screen navigation)
    fun withArgs(vararg args: String): String {
        return buildString {
            append(path)
            args.forEach{ arg ->
                append("/$arg")
            }
        }
    }

    // build and setup route format (in navigation graph)
    fun withArgsFormat(vararg args: String) : String {
        return buildString {
            append(path)
            args.forEach{ arg ->
                append("/{$arg}")
            }
        }
    }
}
