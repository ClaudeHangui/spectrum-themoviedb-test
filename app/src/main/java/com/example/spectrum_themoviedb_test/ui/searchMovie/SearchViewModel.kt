package com.example.spectrum_themoviedb_test.ui.searchMovie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spectrum_themoviedb_test.data.MoviesRepository
import com.example.spectrum_themoviedb_test.ui.homepage.state.MoviesState
import com.example.spectrum_themoviedb_test.ui.homepage.state.PaginationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _searchedMoviesState = MutableStateFlow(MoviesState())
    val searchedMoviesState = _searchedMoviesState

    private val _paginationState = MutableStateFlow(PaginationState())
    val paginationState = _paginationState.asStateFlow()

    private val _isResetScreenState = MutableStateFlow(false)
    val isResetScreenState: StateFlow<Boolean> = _isResetScreenState.asStateFlow()

    var previousQuery: String? = null

    fun performQuery(query: String, pageNumber: Int) = viewModelScope.launch {
        if (query.isNotEmpty() && !query.equals(previousQuery, ignoreCase = true)) {
            resetScreen()
        }

        moviesRepository.searchMovie(query, pageNumber)
            .distinctUntilChanged()
            .onStart {
                _searchedMoviesState.update { it.copy(isScreenInit = false) }
                if (_searchedMoviesState.value.movies.isEmpty()) {
                    _searchedMoviesState.update { it.copy(isLoading = true) }
                }

                if (_searchedMoviesState.value.movies.isNotEmpty()) {
                    _paginationState.update { it.copy(isLoading = true) }
                }

            }.catch { error ->
                _searchedMoviesState.update {
                    it.copy(
                        throwable = error,
                        isLoading = false
                    )
                }
            }.collect { response ->
                previousQuery = query
                _searchedMoviesState.update { movieState ->
                    movieState.copy(
                        movies = movieState.movies + response.data,
                        isLoading = false,
                        nextPageToView = movieState.nextPageToView + 1,
                        isNoMovieFound = movieState.movies.isEmpty() && response.data.isEmpty(),
                        throwable = null
                    )
                }

                _paginationState.update { paginationState ->
                    paginationState.copy(
                        isLoading = false,
                        totalPages = if (_paginationState.value.totalPages <= response.totalPages) response.totalPages else _paginationState.value.totalPages,
                        endReached = response.data.isEmpty() || _searchedMoviesState.value.nextPageToView > response.totalPages
                    )
                }
            }
    }

    private fun resetScreen() {
        viewModelScope.launch {
            _isResetScreenState.emit(true)
            _searchedMoviesState.update {
                it.copy(
                    movies = emptyList(),
                    isLoading = false,
                    throwable = null,
                    isNoMovieFound = false,
                    isScreenInit = false,
                    nextPageToView = 1
                )
            }
            _paginationState.update {
                it.copy(
                    isLoading = false,
                    totalPages = 1,
                    endReached = false
                )
            }
            _isResetScreenState.emit(false)
        }
    }

    fun loadMoreMovies() {
        if (!_paginationState.value.endReached && _searchedMoviesState.value.movies.isNotEmpty()) {
            performQuery(previousQuery ?: "", _searchedMoviesState.value.nextPageToView)
        }
    }
}