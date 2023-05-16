package com.example.spectrum_themoviedb_test.data.remote

import com.example.spectrum_themoviedb_test.data.GenreApiResponse
import com.example.spectrum_themoviedb_test.data.MovieDetailApiResponse
import com.example.spectrum_themoviedb_test.data.MoviesApiResponse
import com.example.spectrum_themoviedb_test.data.PopularMoviesApiResponse
import com.example.spectrum_themoviedb_test.data.ResultState
import javax.inject.Inject

class RemoteDataStore @Inject constructor(
    private val api: MovieDbApi,
    private val httpFailureFactory: HttpFailureFactory
) {
    suspend fun fetchNowPlayingMovies(page: Int): ResultState<MoviesApiResponse?> {
        return try {
            val apiResponse = api.getNowPlayingMovies(page)
            ResultState.Success(apiResponse)
        } catch (e: Exception) {
            ResultState.Failure(httpFailureFactory.produceFailure(e), null)
        }
    }

    suspend fun fetchTopRatedMovies(page: Int): ResultState<PopularMoviesApiResponse?> {
        return try {
            val apiResponse = api.getTopRatedMovies(page)
            ResultState.Success(apiResponse)
        } catch (e: Exception) {
            ResultState.Failure(httpFailureFactory.produceFailure(e), null)
        }
    }

    suspend fun fetchUpcomingVideos(page: Int): ResultState<MoviesApiResponse?> {
        return try {
            val apiResponse = api.getUpComing(page)
            ResultState.Success(apiResponse)
        } catch (e: Exception) {
            ResultState.Failure(httpFailureFactory.produceFailure(e), null)
        }
    }

    suspend fun fetchSearchMovies(query: String, page: Int): ResultState<PopularMoviesApiResponse?> {
        return try {
            val apiResponse = api.getSearchedMovies(query, page)
            ResultState.Success(apiResponse)
        } catch (e: Exception) {
            ResultState.Failure(httpFailureFactory.produceFailure(e), null)
        }
    }

    suspend fun fetchMovieDetail(movieId: Int): ResultState<MovieDetailApiResponse> {
        return try {
            val apiResponse = api.getMovieDetail(movieId)
            ResultState.Success(apiResponse)
        } catch (e: Exception) {
            ResultState.Failure(httpFailureFactory.produceFailure(e), null)
        }
    }

    suspend fun fetchMovieGenres(): ResultState<GenreApiResponse> {
        return try {
            val apiResponse = api.getMovieGenres()
            ResultState.Success(apiResponse)
        } catch (e: Exception) {
            ResultState.Failure(httpFailureFactory.produceFailure(e), null)
        }
    }

    suspend fun fetchPopularMovies(page: Int): ResultState<PopularMoviesApiResponse?> {
        return try {
            val apiResponse = api.getPopularMovies(page)
            ResultState.Success(apiResponse)
        } catch (e: Exception) {
            ResultState.Failure(httpFailureFactory.produceFailure(e), null)
        }
    }
}