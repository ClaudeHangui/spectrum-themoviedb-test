package com.example.spectrum_themoviedb_test.data.mapper

import com.example.spectrum_themoviedb_test.data.BaseApiResponse
import com.example.spectrum_themoviedb_test.data.ResultState
import com.example.spectrum_themoviedb_test.data.UiMovieItem
import com.example.spectrum_themoviedb_test.data.local.MoviesDao
import com.example.spectrum_themoviedb_test.util.DateFormatterHelper
import javax.inject.Inject

class MovieListMapper @Inject constructor(
    private val dao: MoviesDao,
    private val dateFormatter: DateFormatterHelper
) {
    fun mapToUIModel(response: BaseApiResponse?) =
        response?.let {
            if (it.results.isEmpty()) {
                emptyList()
            } else {
               it.results.map { movie ->
                    val movieGenres = movie.genre_ids.map { genreId ->
                        dao.getGenre(genreId)
                    }
                    val formattedDate = dateFormatter.formatDate(movie.release_date, DATE_PATTERN)

                    UiMovieItem(
                        movie.id,
                        movie.title,
                        movie.poster_path,
                        movie.vote_average,
                        movie.vote_count,
                        formattedDate,
                        movieGenres
                    )
                }
            }
        } ?: listOf(UiMovieItem.NO_DATA)

    companion object {
        const val DATE_PATTERN = "dd/mmm/yyyy"
    }
}