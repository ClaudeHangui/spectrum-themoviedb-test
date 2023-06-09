package com.example.spectrum_themoviedb_test.data

import com.example.spectrum_themoviedb_test.IoDispatcher
import com.example.spectrum_themoviedb_test.data.local.MoviesDao
import com.example.spectrum_themoviedb_test.data.mapper.MovieDetailMapper
import com.example.spectrum_themoviedb_test.data.mapper.MovieListMapper
import com.example.spectrum_themoviedb_test.data.mapper.MoviesModel
import com.example.spectrum_themoviedb_test.data.model.UiMovieDetail
import com.example.spectrum_themoviedb_test.data.remote.MovieDbApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class MoviesRepository @Inject constructor(
    private val dao: MoviesDao,
    private val movieListMapper: MovieListMapper,
    private val movieDetailMapper: MovieDetailMapper,
    private val api: MovieDbApi,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher
) {

    fun fetchNowPlayingMovies(page: Int): Flow<MoviesModel> = flow {
        val apiResponse = api.getNowPlayingMovies(page)
        val mapResult = movieListMapper.mapToUIModel(apiResponse)
        emit(mapResult)
    }.flowOn(ioDispatcher)


    fun fetchTopRatedMovies(page: Int) = flow {
        val apiResponse = api.getTopRatedMovies(page)
        val mapResult = movieListMapper.mapToUIModel(apiResponse)
        emit(mapResult)
    }.flowOn(ioDispatcher)

    fun fetchUpcomingVideos(page: Int) = flow {
        val apiResponse = api.getUpComing(page)
        val mapResult = movieListMapper.mapToUIModel(apiResponse)
        emit(mapResult)
    }.flowOn(ioDispatcher)

    fun fetchPopularMovies(movieId: Int) = flow {
        val apiResponse = api.getPopularMovies(movieId)
        val mapResult = movieListMapper.mapToUIModel(apiResponse)
        emit(mapResult)
    }.flowOn(ioDispatcher)

    fun addMovieToBookmarks(movie: UiMovieDetail) = dao.bookmarkMovie(movie).toInt()

    fun removeMovieFromBookmarks(movie: UiMovieDetail) = dao.unBookmarkMovie(movie)

    fun getBookmarkedMovies() = dao.getAllBookmarkedMovies()

    fun searchMovie(query: String, page: Int) = flow {
        val apiResponse = api.getSearchedMovies(query, page)
        val mapResult = movieListMapper.mapToUIModel(apiResponse)
        emit(mapResult)
    }.flowOn(ioDispatcher)

    fun fetchMovieDetail(movieId: Int): Flow<UiMovieDetail> = flow {
        val apiResponse = api.getMovieDetail(movieId)
        val isMovieAlreadyBookmarked = dao.getMovie(movieId) != null
        val mapResult = movieDetailMapper.mapToUIModel(apiResponse, isMovieAlreadyBookmarked)
        emit(mapResult)
    }.flowOn(ioDispatcher)

    suspend fun fetchMovieGenres() {
        try {
            val apiResponse = api.getMovieGenres()
            dao.insertAllGenres(apiResponse.genres)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}