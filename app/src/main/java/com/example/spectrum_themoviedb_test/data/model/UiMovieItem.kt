package com.example.spectrum_themoviedb_test.data.model

data class UiMovieItem(
    override val movieId: Int,
    override val posterPath: String?,
    override val title: String?,
    override val voteAverage: Double?,
    override val voteCount: Int?,
    override val releaseDate: String?,
    override val genre: List<String>
): BaseUiMovieModel {
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

