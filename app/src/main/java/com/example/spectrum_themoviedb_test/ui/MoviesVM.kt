package com.example.spectrum_themoviedb_test.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spectrum_themoviedb_test.data.MoviesRepository
import com.example.spectrum_themoviedb_test.data.model.UiMovieItem
import com.example.spectrum_themoviedb_test.data.remote.HttpFailureFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesVM @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val failureFactory: HttpFailureFactory
) : ViewModel() {

    var nowPlayingUiState = MutableStateFlow(NowPlayingMoviesResult())

    private val _topRatedUiState = MutableStateFlow(emptyList<UiMovieItem>())
    val topRatedUiState = _topRatedUiState.asStateFlow()

    private val _upcomingUiState = MutableStateFlow(emptyList<UiMovieItem>())
    val upcomingUiState = _upcomingUiState.asStateFlow()

    private val _popularUiState = MutableStateFlow(emptyList<UiMovieItem>())
    val popularUiState = _popularUiState.asStateFlow()

    init {
        getNowPlayingMovies()
    }
    private fun getNowPlayingMovies() = viewModelScope.launch {
        moviesRepository.fetchNowPlayingMovies(1).onStart {
            nowPlayingUiState.value = nowPlayingUiState.value.copy(isLoading = true)
        }.catch {
            it.printStackTrace()
            nowPlayingUiState.value = nowPlayingUiState.value.copy(failureType = it, isLoading = false)
        }.collect {
            nowPlayingUiState.value = nowPlayingUiState.value.copy(result = it, isLoading = false)
        }
    }
}

data class NowPlayingMoviesResult(
    val result: List<UiMovieItem> = emptyList(),
    val failureType: Throwable? = null,
    val isLoading: Boolean = false
)