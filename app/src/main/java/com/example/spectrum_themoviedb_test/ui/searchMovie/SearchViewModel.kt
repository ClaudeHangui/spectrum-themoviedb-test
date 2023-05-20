package com.example.spectrum_themoviedb_test.ui.searchMovie

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spectrum_themoviedb_test.data.MoviesRepository
import com.example.spectrum_themoviedb_test.ui.homepage.state.MoviesState
import com.example.spectrum_themoviedb_test.ui.homepage.state.PaginationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
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

    var previousQuery: String? = null

    fun performQuery(query: String, pageNumber: Int) = viewModelScope.launch {
        if (!previousQuery.equals(query, ignoreCase = true)) {
            Log.e("SearchViewModel", "performQuery: resetting screen")
            resetScreen()
            moviesRepository.searchMovie(query, pageNumber)
                .distinctUntilChanged()
                .onStart {
                    if (_searchedMoviesState.value.movies.isEmpty()) {
                        _searchedMoviesState.value =
                            _searchedMoviesState.value.copy(isLoading = true)
                    }

                    if (_searchedMoviesState.value.movies.isNotEmpty()) {
                        _paginationState.update { it.copy(isLoading = true) }
                    }

                }.catch { error ->
                    _searchedMoviesState.update {
                        it.copy(
                            throwable = error.message ?: "Something went wrong",
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
                            throwable = null
                        )
                    }

                    _paginationState.update { paginationState ->
                        paginationState.copy(
                            isLoading = false,
                            totalPages = if (_paginationState.value.totalPages <= response.totalPages) response.totalPages else _paginationState.value.totalPages,
                            endReached = response.data.isEmpty() || _searchedMoviesState.value.nextPageToView >= response.totalPages
                        )
                    }
                }
        }
    }

    private fun resetScreen() {
        _searchedMoviesState.update { it.copy(movies = emptyList(), isLoading = false, throwable = null, nextPageToView = 1) }
        _paginationState.value = PaginationState()
    }

    fun loadMoreMovies() {
        if (!_paginationState.value.endReached && _searchedMoviesState.value.movies.isNotEmpty()) {
            Log.e("TAG", "getMoviesPaginated: End reached")
            //searchMovies(_searchedMoviesState.value.nextPageToView)
        }
    }
}