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
class PopularMoviesVM @Inject constructor(
    private val moviesRepository: MoviesRepository): ViewModel() {

    private val _isRefreshPopularScreen = MutableStateFlow(false)
    val isRefreshPopularScreenState: StateFlow<Boolean> = _isRefreshPopularScreen.asStateFlow()

    private val _paginationState = MutableStateFlow(PaginationState())
    val paginationState = _paginationState.asStateFlow()

    private val _popularMoviesState = MutableStateFlow(MoviesState())
    val popularMoviesState = _popularMoviesState.asStateFlow()

    init {
        if (_popularMoviesState.value.movies.isEmpty()) {
            getPopularVideos(1)
        }
    }

    private fun getPopularVideos(pageNumber: Int) = viewModelScope.launch {
        moviesRepository.fetchPopularMovies(pageNumber)
            .distinctUntilChanged()
            .onStart {
                if (_popularMoviesState.value.movies.isEmpty()) {
                    _popularMoviesState.update { it.copy(isLoading = true) }
                }

                if (_popularMoviesState.value.movies.isNotEmpty()) {
                    _paginationState.update { it.copy(isLoading = true) }
                }
            }.catch { error ->
                _popularMoviesState.update {
                    it.copy(throwable = error, isLoading = false)
                }
                _paginationState.update {
                    it.copy(isLoading = false)
                }
            }.collect { response ->
                _popularMoviesState.update { popularMoviesState ->
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
                        endReached = response.data.isEmpty() || _popularMoviesState.value.nextPageToView > response.totalPages
                    )
                }
            }
    }

    fun refreshPopularScreen() {
        viewModelScope.launch {
            _isRefreshPopularScreen.emit(true)
            _popularMoviesState.update {
                it.copy(
                    nextPageToView = 1,
                    movies = emptyList(),
                    throwable = null
                )
            }
            getPopularVideos(1)
            _isRefreshPopularScreen.emit(false)
        }
    }

    fun loadMorePopularMovies() {
        if (!_paginationState.value.endReached && _popularMoviesState.value.movies.isNotEmpty()) {
            getPopularVideos(_popularMoviesState.value.nextPageToView)
        }
    }

}