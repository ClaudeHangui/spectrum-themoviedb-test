package com.example.spectrum_themoviedb_test.data.mapper

import com.example.spectrum_themoviedb_test.data.MovieDetailApiResponse
import com.example.spectrum_themoviedb_test.data.model.UiMovieDetail
import com.example.spectrum_themoviedb_test.util.DateFormatterHelper
import javax.inject.Inject

class MovieDetailMapper @Inject constructor(private val dateFormatterHelper: DateFormatterHelper) {
    fun mapToUIModel(movieDetail: MovieDetailApiResponse): UiMovieDetail {
        val movieGenres = if (movieDetail.genres.isEmpty()) {
            emptyList()
        } else {
            movieDetail.genres.map { it.name }
        }

        val languagesAvailable = if (movieDetail.spoken_languages.isEmpty()) {
            emptyList()
        } else {
            movieDetail.spoken_languages.map { it.english_name }
        }

        return UiMovieDetail(
            movieId = movieDetail.id,
            title = movieDetail.title,
            backDropPath = movieDetail.backdrop_path,
            posterPath = movieDetail.poster_path,
            voteAverage = movieDetail.vote_average,
            voteCount = movieDetail.vote_count,
            releaseDate =
            dateFormatterHelper.formatDate(movieDetail.release_date, DATE_PATTERN),
            genre = movieGenres,
            overview = movieDetail.overview,
            status = movieDetail.status,
            tagLine = movieDetail.tagline,
            spokenLanguages = languagesAvailable
        )
    }

    companion object {
        const val DATE_PATTERN = "dd/MMM/yyyy"
    }
}