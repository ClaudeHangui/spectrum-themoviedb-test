package com.example.spectrum_themoviedb_test.data

import com.example.spectrum_themoviedb_test.data.local.MoviesDao
import com.example.spectrum_themoviedb_test.data.remote.MovieDbApi
import com.example.spectrum_themoviedb_test.util.DateFormatterHelper
import javax.inject.Inject


class MoviesRepository @Inject constructor(
    private val api: MovieDbApi,
    private val dao: MoviesDao,
    private val dateFormatter: DateFormatterHelper
) {

    suspend fun fetchNowPlayingMovies(page: Int): ResultState<List<UiMovieItem>> {
        return try {
            val response = api.getNowPlayingMovies(page)
            response?.let {
                if (response.results.isEmpty()) {
                    ResultState.Success(emptyList())
                } else {
                    val successfulResponse = response.results
                    ResultState.Success(successfulResponse.map { movie ->
                        val movieGenres = movie.genre_ids.map {
                            dao.getGenre(it)
                        }
                        val formattedDate = dateFormatter.formatDate(movie.release_date)
                        UiMovieItem(
                            movieId = movie.id,
                            posterPath = movie.poster_path,
                            title = movie.title,
                            voteAverage = movie.vote_average,
                            voteCount = movie.vote_count,
                            releaseDate = formattedDate,
                            genre = movieGenres
                        )
                    })
                }
            } ?: ResultState.Success(listOf(UiMovieItem.NO_DATA))

        } catch (e: Exception) {
            ResultState.Failure(null)
        }
    }
}