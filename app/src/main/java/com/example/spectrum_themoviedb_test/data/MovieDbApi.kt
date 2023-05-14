package com.example.spectrum_themoviedb_test.data

import com.example.spectrum_themoviedb_test.util.Constants.API_KEY
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDbApi {
    @GET("3/movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): ApiResponse<MoviesApiResponse>

    @GET("3/movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): ApiResponse<PopularMoviesApiResponse>

    @GET("3/movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): ApiResponse<PopularMoviesApiResponse>

    @GET("3/movie/upcoming")
    suspend fun getUpComing(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): ApiResponse<MoviesApiResponse>

    @GET("3/genre/movie/list")
    suspend fun getAvailableGenres(
        @Query("api_key") apiKey: String = API_KEY
    ): ApiResponse<GenreApiResponse>

    @GET("3/movie/{movie-id}")
    suspend fun getMovieDetail(
        @Path("movie-id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): ApiResponse<MovieDetailApiResponse>

    @GET("3/search/movie")
    suspend fun getSearchedMovies(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): ApiResponse<PopularMoviesApiResponse>
}