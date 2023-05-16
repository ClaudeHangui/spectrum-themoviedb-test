package com.example.spectrum_themoviedb_test.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spectrum_themoviedb_test.data.MoviesRepository
import com.example.spectrum_themoviedb_test.data.ResultState
import com.example.spectrum_themoviedb_test.data.model.UiMovieItem
import com.example.spectrum_themoviedb_test.util.InternetStateManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesVM @Inject constructor(
    private val internetStateManager: InternetStateManager,
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _internetUiState = MutableStateFlow(false)
    val internetUiState = _internetUiState.asStateFlow()

    private val _nowPlayingUiState = MutableStateFlow(emptyList<UiMovieItem>())
    val nowPlayingUiState = _nowPlayingUiState.asStateFlow()

    private val _topRatedUiState = MutableStateFlow(emptyList<UiMovieItem>())
    val topRatedUiState = _topRatedUiState.asStateFlow()

    private val _upcomingUiState = MutableStateFlow(emptyList<UiMovieItem>())
    val upcomingUiState = _upcomingUiState.asStateFlow()

    private val _popularUiState = MutableStateFlow(emptyList<UiMovieItem>())
    val popularUiState = _popularUiState.asStateFlow()

    fun monitorInternetStateChange() = viewModelScope.launch {
        internetStateManager.observeNetworkChangeState().collectLatest {
            _internetUiState.value = it
        }
    }

}