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
class UpcomingMoviesVM @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _isRefreshUpcomingScreen = MutableStateFlow(false)
    val isRefreshUpcomingScreen: StateFlow<Boolean> = _isRefreshUpcomingScreen.asStateFlow()

    private val _paginationState = MutableStateFlow(PaginationState())
    val paginationState = _paginationState.asStateFlow()

    private val _upcomingMoviesState = MutableStateFlow(MoviesState())
    val upcomingMoviesState = _upcomingMoviesState.asStateFlow()

    init {
        if (_upcomingMoviesState.value.movies.isEmpty()) {
            getUpcomingMovies(1)
        }
    }

    private fun getUpcomingMovies(pageNumber: Int) = viewModelScope.launch {
        moviesRepository.fetchUpcomingVideos(pageNumber)
            .distinctUntilChanged()
            .onStart {
                if (_upcomingMoviesState.value.movies.isEmpty()) {
                    _upcomingMoviesState.update { it.copy(isLoading = true) }
                }

                if (_upcomingMoviesState.value.movies.isNotEmpty()) {
                    _paginationState.update { it.copy(isLoading = true) }
                }
            }.catch { error ->
                _upcomingMoviesState.update {
                    it.copy(throwable = error, isLoading = false)
                }
            }.collect { response ->
                _upcomingMoviesState.update { popularMoviesState ->
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
                        endReached = response.data.isEmpty() || _upcomingMoviesState.value.nextPageToView > response.totalPages
                    )
                }
            }
    }

    fun refreshUpcomingScreen() {
        viewModelScope.launch {
            _isRefreshUpcomingScreen.emit(true)
            _upcomingMoviesState.update {
                it.copy(
                    nextPageToView = 1,
                    movies = emptyList(),
                    throwable = null
                )
            }
            getUpcomingMovies(1)
            _isRefreshUpcomingScreen.emit(false)
        }
    }

    fun loadMoreUpcomingMovies() {
        if (!_paginationState.value.endReached && _upcomingMoviesState.value.movies.isNotEmpty()) {
            getUpcomingMovies(_upcomingMoviesState.value.nextPageToView)
        }
    }

}