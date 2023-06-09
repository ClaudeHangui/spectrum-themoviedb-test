package com.example.spectrum_themoviedb_test.ui.homepage.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.spectrum_themoviedb_test.ui.commons.ShowLoader
import com.example.spectrum_themoviedb_test.ui.coreNavigationGraph.Destinations
import com.example.spectrum_themoviedb_test.ui.homepage.components.FailureState
import com.example.spectrum_themoviedb_test.ui.homepage.components.InfiniteListHandler
import com.example.spectrum_themoviedb_test.ui.homepage.components.MovieCard
import com.example.spectrum_themoviedb_test.ui.homepage.viewwmodels.UpcomingMoviesVM
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UpcomingScreen(
    navController: NavController,
    viewModel: UpcomingMoviesVM = hiltViewModel()
) {

    val myScaffoldState = rememberScaffoldState()
    val lazyListState = rememberLazyStaggeredGridState()
    val state by viewModel.upcomingMoviesState.collectAsStateWithLifecycle()
    val paginationState by viewModel.paginationState.collectAsStateWithLifecycle()
    val refreshState by viewModel.isRefreshUpcomingScreen.collectAsStateWithLifecycle()

    Scaffold(scaffoldState = myScaffoldState) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
                .background(color = Color.LightGray)
        ) {

            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = refreshState),
                onRefresh = { viewModel.refreshUpcomingScreen() }) {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(6.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalItemSpacing = 10.dp,
                    state = lazyListState,
                ) {
                    val movies = state.movies
                    Log.e("NowPlayingScreen", "total number of pages: ${state.nextPageToView}")
                    items(movies) { movieItem ->
                        MovieCard(
                            movieItem = movieItem,
                            onClick = {
                                navController.navigate(Destinations.MovieDetailScreen.route + "/${movieItem.movieId}")
                            })
                    }
                    item {
                        if (paginationState.isLoading) {
                            Log.e("NowPlayingScreen", "Loading...")
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }

                InfiniteListHandler(lazyListState = lazyListState) {
                    viewModel.loadMoreUpcomingMovies()
                }

            }

            UpcomingScreenState()
        }
    }
}

@Composable
fun BoxScope.UpcomingScreenState(viewModel: UpcomingMoviesVM = hiltViewModel()) {
    val state by viewModel.upcomingMoviesState.collectAsStateWithLifecycle()
    state.let {
        if (it.isLoading) {
            ShowLoader()
        }
        FailureState(throwable = it.throwable, data = it.movies) {
            if (it.movies.isEmpty()) {
                viewModel.loadMoreUpcomingMovies()
            } else {
                viewModel.refreshUpcomingScreen()
            }
        }
    }
}