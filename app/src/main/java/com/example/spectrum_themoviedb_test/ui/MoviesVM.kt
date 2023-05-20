package com.example.spectrum_themoviedb_test.ui

import android.util.Log
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
class MoviesVM @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _isRefresh = MutableStateFlow(false)
    val isRefresh: StateFlow<Boolean> = _isRefresh.asStateFlow()

    private val _paginationState = MutableStateFlow(PaginationState())
    val paginationState = _paginationState.asStateFlow()

    private val _nowPlayingState = MutableStateFlow(MoviesState())
    val nowPlayingState = _nowPlayingState.asStateFlow()

    init {
        if (_nowPlayingState.value.movies.isEmpty()) {
            Log.e("MoviesVM", "nowPlayingState is empty")
            getNowPlayingVideos(1)
        }
    }


    private fun getNowPlayingVideos(pageNumber: Int) = viewModelScope.launch {
        moviesRepository.fetchNowPlayingMovies(pageNumber)
            .distinctUntilChanged()
            .onStart {
                if (_nowPlayingState.value.movies.isEmpty()) {
                    _nowPlayingState.update { it.copy(isLoading = true) }
                }

                if (_nowPlayingState.value.movies.isNotEmpty()) {
                    _paginationState.update { it.copy(isLoading = true) }
                }

            }.catch { error ->
                error.printStackTrace()
                _nowPlayingState.update {
                    it.copy(throwable = error.message ?: "Something went wrong", isLoading = false)
                }
            }.collect { response ->
                Log.e("MoviesVM", "updating the local mutable flow")
                _nowPlayingState.update { movieState ->
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
                        endReached = response.data.isEmpty() || _nowPlayingState.value.nextPageToView > response.totalPages
                    )
                }
            }
    }

    fun refreshMovieList() {
        viewModelScope.launch {
            _isRefresh.emit(true)
            _nowPlayingState.update {
                it.copy(
                    nextPageToView = 1,
                    movies = emptyList(),
                    throwable = null
                )
            }
            getNowPlayingVideos(1)
            _isRefresh.emit(false)
        }
    }

    fun loadMoreMovies() {
        if (!_paginationState.value.endReached && _nowPlayingState.value.movies.isNotEmpty()) {
            Log.e("TAG", "getMoviesPaginated: End reached")
            getNowPlayingVideos(_nowPlayingState.value.nextPageToView)
        }
    }
}