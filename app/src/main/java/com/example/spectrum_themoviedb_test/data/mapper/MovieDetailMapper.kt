package com.example.spectrum_themoviedb_test.data.mapper

import com.example.spectrum_themoviedb_test.data.MovieDetailApiResponse
import com.example.spectrum_themoviedb_test.data.UiMovieDetail
import com.example.spectrum_themoviedb_test.util.DateFormatterHelper
import javax.inject.Inject

class MovieDetailMapper @Inject constructor(private val dateFormatterHelper: DateFormatterHelper) {
    fun mapToUIModel(movieDetail: MovieDetailApiResponse): UiMovieDetail {
        return UiMovieDetail(
            movieId = movieDetail.id,
            posterPath = movieDetail.poster_path,
            title = movieDetail.title,
            voteAverage = movieDetail.vote_average,
            voteCount = movieDetail.vote_count,
            releaseDate = dateFormatterHelper.formatDate(movieDetail.release_date, DATE_PATTERN),
            genre = movieDetail.genres.map { it.name },
            overview = movieDetail.overview,
            backDropPath = movieDetail.backdrop_path,
            status = movieDetail.status
        )
    }

    companion object {
        const val DATE_PATTERN = "dd/mmm/yyyy"
    }
}