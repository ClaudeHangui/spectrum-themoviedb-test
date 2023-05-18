package com.example.spectrum_themoviedb_test.ui.homepage.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.spectrum_themoviedb_test.ui.MoviesVM
import com.example.spectrum_themoviedb_test.ui.homepage.components.MovieCard

@OptIn(ExperimentalFoundationApi::class)

@Composable
fun NowPlayingScreen(viewModel: MoviesVM = hiltViewModel()) {
    val scrollState = rememberScrollState()
    Surface(modifier = Modifier.fillMaxSize()) {

    }
    /*
    val moviesState by remember {
        viewModel.nowPlayingUiState
    }
    */
    val state by viewModel.nowPlayingUiState.collectAsState()


    state.let { movies ->
        println("NowPlayingScreen: moviesState.result.size = ${movies}")
        LazyVerticalStaggeredGrid (
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier.fillMaxWidth()
                .wrapContentHeight()
                .padding(6.dp)
            ,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalItemSpacing = 10.dp
        ){
            items(movies.result) { movieItem ->
                MovieCard(movieItem = movieItem, onClick = { })
            }
        }
    }

    /*
    moviesState.failureType.let {
        println("NowPlayingScreen: moviesState.failureType = $it")
    }
    */

    /*
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = stringResource(id = R.string.screen_title_now_playing))
    }
    */
}