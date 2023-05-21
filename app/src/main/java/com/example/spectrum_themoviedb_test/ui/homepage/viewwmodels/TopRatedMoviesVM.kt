package com.example.spectrum_themoviedb_test.ui.homepage.viewwmodels

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
class TopRatedMoviesVM @Inject constructor(
    private val moviesRepository: MoviesRepository
): ViewModel() {

    private val _isRefreshTopRatedScreen = MutableStateFlow(false)
    val isRefreshTopRatedScreen: StateFlow<Boolean> = _isRefreshTopRatedScreen.asStateFlow()

    private val _paginationState = MutableStateFlow(PaginationState())
    val paginationState = _paginationState.asStateFlow()

    private val _topRatedMoviesState = MutableStateFlow(MoviesState())
    val topRatedMoviesState = _topRatedMoviesState.asStateFlow()

    init {
        if (_topRatedMoviesState.value.movies.isEmpty()) {
            getTopRatedMovies(1)
        }
    }
    private fun getTopRatedMovies(pageNumber: Int) = viewModelScope.launch {
        moviesRepository.fetchTopRatedMovies(pageNumber)
            .distinctUntilChanged()
            .onStart {
                if (_topRatedMoviesState.value.movies.isEmpty()) {
                    _topRatedMoviesState.update { it.copy(isLoading = true) }
                }

                if (_topRatedMoviesState.value.movies.isNotEmpty()) {
                    _paginationState.update { it.copy(isLoading = true) }
                }
            }.catch { error ->
                _topRatedMoviesState.update {
                    it.copy(throwable = error, isLoading = false)
                }
            }.collect { response ->
                _topRatedMoviesState.update { popularMoviesState ->
                    popularMoviesState.copy(
                        movies = popularMoviesState.movies + response.data,
                        isLoading = false,
                        nextPageToView = popularMoviesState.nextPageToView + 1,
                        throwable = null
                    )
                }

                _paginationState.update { paginationState ->
                    paginationState.copy(
                        isLoading = false,
                        totalPages = if (_paginationState.value.totalPages <= response.totalPages) response.totalPages else _paginationState.value.totalPages,
                        endReached = response.data.isEmpty() || _topRatedMoviesState.value.nextPageToView > response.totalPages
                    )
                }
            }
    }


    fun refreshTopRatedScreen() {
        viewModelScope.launch {
            _isRefreshTopRatedScreen.emit(true)
            _topRatedMoviesState.update {
                it.copy(
                    nextPageToView = 1,
                    movies = emptyList(),
                    throwable = null
                )
            }
            getTopRatedMovies(1)
            _isRefreshTopRatedScreen.emit(false)
        }
    }

    fun loadMoreTopRatedMovies() {
        if (!_paginationState.value.endReached && _topRatedMoviesState.value.movies.isNotEmpty()) {
            getTopRatedMovies(_topRatedMoviesState.value.nextPageToView)
        }
    }
}