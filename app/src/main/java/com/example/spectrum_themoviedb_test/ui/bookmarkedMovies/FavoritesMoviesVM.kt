package com.example.spectrum_themoviedb_test.ui.bookmarkedMovies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spectrum_themoviedb_test.data.MoviesRepository
import com.example.spectrum_themoviedb_test.ui.homepage.state.MoviesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoritesMoviesVM @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _favoritesMoviesState = MutableStateFlow(MoviesState())
    val favoritesMoviesState = _favoritesMoviesState

    init {
        getBookmarkedMovies()
    }

    private fun getBookmarkedMovies() = viewModelScope.launch(Dispatchers.Main) {
        _favoritesMoviesState.update { it.copy(isLoading = true) }
        val result = withContext(Dispatchers.IO) {
            moviesRepository.getBookmarkedMovies()
        }

        _favoritesMoviesState.update { it.copy(isLoading = false, movies = result) }
    }
}