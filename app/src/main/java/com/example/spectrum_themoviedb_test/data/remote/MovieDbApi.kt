package com.example.spectrum_themoviedb_test.data.remote

import com.example.spectrum_themoviedb_test.data.model.GenreApiResponse
import com.example.spectrum_themoviedb_test.data.model.MovieDetailApiResponse
import com.example.spectrum_themoviedb_test.data.model.MoviesApiResponse
import com.example.spectrum_themoviedb_test.data.model.PopularMoviesApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDbApi {
    @GET("3/movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int
    ): MoviesApiResponse?

    @GET("3/movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int
    ): PopularMoviesApiResponse?

    @GET("3/movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int
    ): PopularMoviesApiResponse?

    @GET("3/movie/upcoming")
    suspend fun getUpComing(
        @Query("page") page: Int
    ): MoviesApiResponse?

    @GET("3/genre/movie/list")
    suspend fun getMovieGenres(): GenreApiResponse

    @GET("3/movie/{movie-id}")
    suspend fun getMovieDetail(
        @Path("movie-id") movieId: Int
    ): MovieDetailApiResponse

    @GET("3/search/movie")
    suspend fun getSearchedMovies(
        @Query("query") query: String,
        @Query("page") page: Int
    ): PopularMoviesApiResponse
}