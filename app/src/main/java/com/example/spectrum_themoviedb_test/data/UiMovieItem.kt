package com.example.spectrum_themoviedb_test.data

data class UiMovieItem(
    val movieId: Int,
    val posterPath: String,
    val title: String,
    val voteAverage: Double,
    val voteCount: Int,
    val releaseDate: String,
    val genre: List<String>
) {
    companion object {
        val NO_DATA = UiMovieItem(
            movieId = 0,
            posterPath = "",
            title = "",
            voteAverage = 0.0,
            voteCount = 0,
            releaseDate = "",
            genre = emptyList()
        )
    }
}

