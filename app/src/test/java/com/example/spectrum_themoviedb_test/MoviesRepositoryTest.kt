package com.example.spectrum_themoviedb_test


import com.example.spectrum_themoviedb_test.data.Dates
import com.example.spectrum_themoviedb_test.data.Genre
import com.example.spectrum_themoviedb_test.data.GenreApiResponse
import com.example.spectrum_themoviedb_test.data.MovieDetailApiResponse
import com.example.spectrum_themoviedb_test.data.MovieItem
import com.example.spectrum_themoviedb_test.data.MoviesApiResponse
import com.example.spectrum_themoviedb_test.data.MoviesRepository
import com.example.spectrum_themoviedb_test.data.ResultState
import com.example.spectrum_themoviedb_test.data.model.UiMovieDetail
import com.example.spectrum_themoviedb_test.data.model.UiMovieItem
import com.example.spectrum_themoviedb_test.data.local.MoviesDao
import com.example.spectrum_themoviedb_test.data.mapper.MovieDetailMapper
import com.example.spectrum_themoviedb_test.data.mapper.MovieListMapper
import com.example.spectrum_themoviedb_test.data.remote.FailureType
import com.example.spectrum_themoviedb_test.data.remote.RemoteDataStore
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class MoviesRepositoryTest {
    @MockK
    lateinit var movieListMapper: MovieListMapper

    @MockK
    lateinit var moviesDao: MoviesDao

    @MockK
    lateinit var movieDetailMapper: MovieDetailMapper

    @MockK
    lateinit var remoteDataStore: RemoteDataStore

    private lateinit var moviesRepository: MoviesRepository

    private val moviesApiResponse = MoviesApiResponse(
        dates = Dates("2020-01-01", "2020-01-02"),
        page = 1,
        results = listOf(
            MovieItem(
                adult = false,
                backdrop_path = "Backdrop Path 1",
                genre_ids = listOf(1, 2),
                id = 1,
                original_language = "Original Language 1",
                original_title = "Original Title 1",
                overview = "Overview 1",
                popularity = 1.0,
                poster_path = "Poster Path 1",
                release_date = "Release Date 1",
                title = "Title 1",
                video = false,
                vote_average = 1.0,
                vote_count = 1
            ),
            MovieItem(
                adult = false,
                backdrop_path = "Backdrop Path 2",
                genre_ids = listOf(3, 4),
                id = 2,
                original_language = "Original Language 2",
                original_title = "Original Title 2",
                overview = "Overview 2",
                popularity = 2.0,
                poster_path = "Poster Path 2",
                release_date = "Release Date 2",
                title = "Title 2",
                video = false,
                vote_average = 2.0,
                vote_count = 2
            )
        ),
        total_results = 2,
        total_pages = 1,
    )

    private val moviesUiModel = listOf(
        UiMovieItem(
            1,
            "Poster Path 1",
            "Title 1",
            1.0,
            1,
            "12/Jun/2020",
            listOf("Action", "Adventure")
        ),
        UiMovieItem(
            2,
            "Poster Path 2",
            "Title 2",
            2.0,
            2,
            "12/Jun/2020",
            listOf("Comedy", "Thriller")
        )
    )

    private val movieDetailApiResponse = MovieDetailApiResponse(
        adult = false,
        backdrop_path = "Backdrop Path 1",
        belongs_to_collection = null,
        budget = 1,
        genres = listOf(MovieDetailApiResponse.Genre(1,"Action"), MovieDetailApiResponse.Genre(2,"Adventure")),
        homepage = "Homepage 1",
        id = 1,
        imdb_id = "IMDB ID 1",
        original_language = "Original Language 1",
        original_title = "Original Title 1",
        overview = "Overview 1",
        popularity = 1.0,
        poster_path = "Poster Path 1",
        production_companies = listOf(MovieDetailApiResponse.ProductionCompany(1, "Production Company 1 path", "Production Company 1", "US")),
        production_countries = listOf(MovieDetailApiResponse.ProductionCountry("Country 1", "Japan")),
        release_date = "Release Date 1",
        revenue = 1,
        runtime = 1,
        spoken_languages = listOf(MovieDetailApiResponse.SpokenLanguage("Language 1", "en", "English")),
        status = "Status 1",
        tagline = "Tagline 1",
        title = "Title 1",
        video = false,
        vote_average = 1.0,
        vote_count = 1
    )

    private val movieDetailUiModel = UiMovieDetail(
        1,
        "Poster Path 1",
        "Title 1",
        1.0,
        1,
        "12/Jun/2020",
        listOf("Action", "Adventure"),
        "Overview 1",
        "Tagline 1",
        "Release Date 1",
    )

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        moviesRepository = MoviesRepository(moviesDao, movieListMapper, movieDetailMapper, remoteDataStore)
    }

    @Test
    fun `when fetching now playing movies, then return a list of movies`() {
        coEvery { remoteDataStore.fetchNowPlayingMovies(1) } returns ResultState.Success(moviesApiResponse)
        coEvery {  movieListMapper.mapToUIModel(moviesApiResponse) } returns moviesUiModel
        val actual = runBlocking { moviesRepository.fetchNowPlayingMovies(1) }
        actual.shouldBeInstanceOf<ResultState.Success<List<UiMovieItem>>>()
    }

    @Test
    fun `when making a call movie detail api, then returns a movie detail`() {
        coEvery { remoteDataStore.fetchMovieDetail(1) } returns ResultState.Success(movieDetailApiResponse)
        coEvery { movieDetailMapper.mapToUIModel(movieDetailApiResponse) } returns movieDetailUiModel
        val actual = runBlocking { moviesRepository.fetchMovieDetail(1) }
        actual.shouldBeInstanceOf<ResultState.Success<UiMovieDetail>>()
    }

    @Test
    fun `when making a call to fetch the movie genre returns a list of genres and save them to the DB`() {
        val movieGenres = listOf(
            Genre(1, "Action"),
            Genre(2, "Adventure"),
            Genre(3, "Comedy")
        )
        coEvery { remoteDataStore.fetchMovieGenres() } returns ResultState.Success(GenreApiResponse(movieGenres))
        coEvery { moviesDao.insertAllGenres(any()) } returns Unit
        runBlocking { moviesRepository.fetchMovieGenres() }
    }

    @Test
    fun `when making api call to fetch top rated movies, fail the request and return a failure`() = runBlocking {
        val remoteFailure = FailureType.NetworkError
        coEvery { remoteDataStore.fetchTopRatedMovies(1) } returns ResultState.Failure(remoteFailure, null)

        val actual =  moviesRepository.fetchTopRatedMovies(1)
        actual `should be instance of` ResultState.Failure::class
    }
}