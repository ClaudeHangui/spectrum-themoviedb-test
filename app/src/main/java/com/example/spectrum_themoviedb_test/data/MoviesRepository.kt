package com.example.spectrum_themoviedb_test.data

import com.example.spectrum_themoviedb_test.data.local.MoviesDao
import com.example.spectrum_themoviedb_test.data.mapper.MovieDetailMapper
import com.example.spectrum_themoviedb_test.data.mapper.MovieListMapper
import com.example.spectrum_themoviedb_test.data.model.UiMovieDetail
import com.example.spectrum_themoviedb_test.data.model.UiMovieItem
import com.example.spectrum_themoviedb_test.data.remote.RemoteDataStore
import javax.inject.Inject


class MoviesRepository @Inject constructor(
    private val dao: MoviesDao,
    private val movieListMapper: MovieListMapper,
    private val movieDetailMapper: MovieDetailMapper,
    private val remoteDataStore: RemoteDataStore
) {


    suspend fun fetchNowPlayingMovies(page: Int): ResultState<List<UiMovieItem>> {
        return when (val result = remoteDataStore.fetchNowPlayingMovies(page)) {
            is ResultState.Success -> {
                val response = result.data
                ResultState.Success(movieListMapper.mapToUIModel(response))
            }

            is ResultState.Failure -> {
                ResultState.Failure(result.failure, null)
            }
        }
    }

    suspend fun fetchTopRatedMovies(page: Int): ResultState<List<UiMovieItem>> {
        return when (val result = remoteDataStore.fetchTopRatedMovies(page)) {
            is ResultState.Success -> {
                val response = result.data
                ResultState.Success(movieListMapper.mapToUIModel(response))
            }

            is ResultState.Failure -> {
                ResultState.Failure(result.failure, null)
            }
        }
    }

    suspend fun fetchUpcomingVideos(page: Int): ResultState<List<UiMovieItem>> {
        return when (val result = remoteDataStore.fetchUpcomingVideos(page)) {
            is ResultState.Success -> {
                val response = result.data
                ResultState.Success(movieListMapper.mapToUIModel(response))
            }

            is ResultState.Failure -> {
                ResultState.Failure(result.failure, null)
            }
        }
    }

    suspend fun fetchSearchMovies(query: String, page: Int): ResultState<List<UiMovieItem>> {
        return when (val result = remoteDataStore.fetchSearchMovies(query, page)) {
            is ResultState.Success -> {
                val response = result.data
                ResultState.Success(movieListMapper.mapToUIModel(response))
            }

            is ResultState.Failure -> {
                ResultState.Failure(result.failure, null)
            }
        }
    }

    suspend fun fetchMovieDetail(movieId: Int): ResultState<UiMovieDetail> {
        return when (val result = remoteDataStore.fetchMovieDetail(movieId)) {
            is ResultState.Success -> {
                val response = result.data
                ResultState.Success(movieDetailMapper.mapToUIModel(response))
            }

            is ResultState.Failure -> {
                ResultState.Failure(result.failure, null)
            }
        }
    }

    suspend fun fetchMovieGenres() {
        when (val result = remoteDataStore.fetchMovieGenres()) {
            is ResultState.Success -> {
                val response = result.data
                dao.insertAllGenres(response.genres)
            }

            is ResultState.Failure -> {}
        }
    }

    suspend fun getPopularMovies(movieId: Int): ResultState<List<UiMovieItem>> {
        return when (val result = remoteDataStore.fetchPopularMovies(movieId)) {
            is ResultState.Success -> {
                val response = result.data
                ResultState.Success(movieListMapper.mapToUIModel(response))
            }

            is ResultState.Failure -> {
                ResultState.Failure(result.failure, null)
            }
        }
    }
}