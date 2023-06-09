package com.example.spectrum_themoviedb_test.ui.searchMovie

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.spectrum_themoviedb_test.R
import com.example.spectrum_themoviedb_test.ui.homepage.components.InfiniteListHandler
import com.example.spectrum_themoviedb_test.ui.homepage.components.MovieCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchMovieScreen(navigateBack: () -> Unit,
                      navigateMovieDetail: (Int) -> Unit,
                      viewModel: SearchViewModel = hiltViewModel()) {

    val scrollState = rememberScrollState()
    val searchMovie = remember { mutableStateOf(TextFieldValue("")) }
    val lazyListState = rememberLazyStaggeredGridState()
    val state by viewModel.searchedMoviesState.collectAsStateWithLifecycle()
    val paginationState by viewModel.paginationState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.LightGray)
    ){
        Column (
            modifier = Modifier,
            verticalArrangement = Arrangement.Top
                ) {
            SearchBar(
                hint = stringResource(id = R.string.search_movie_hint),
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                state = searchMovie,
                onNavigateBack = navigateBack,
                viewModel = viewModel
            )

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
                            navigateMovieDetail(movieItem.movieId)
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
                viewModel.loadMoreMovies()
            }
        }

        SearchScreenState(query = searchMovie.value.text)
    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    hint: String,
    modifier: Modifier = Modifier,
    state: MutableState<TextFieldValue>,
    viewModel: SearchViewModel,
    onNavigateBack: () -> Unit

) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }

    TextField(
        value = state.value,
        onValueChange = { value ->
            state.value = value
        },
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged {
                isHintDisplayed = !it.isFocused
            }
            .focusRequester(focusRequester),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions (
            onDone = {
                viewModel.performQuery(state.value.text, 1)
                focusRequester.freeFocus()
                keyboardController?.hide()
            }
        ),
        textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onPrimary),
        leadingIcon = {
            BackStackButton {
                onNavigateBack()
            }

        },
        trailingIcon = {
            if (state.value.text.isNotEmpty()) {
                IconButton(
                    onClick = {
                        state.value = TextFieldValue("")
                    }
                ) {
                    Icon(
                        Icons.Default.Clear,
                        tint = Color.White,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        placeholder = {
            Text(
                text = hint,
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onPrimary)
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(26.dp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.DarkGray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        )
    )
}

@Composable
fun BackStackButton(
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    onCLick: () -> Unit
) {
    IconButton(onClick = { onCLick() }) {
        Icon(
            tint = color,
            imageVector = Icons.Outlined.ArrowBack,
            contentDescription = null
        )
    }
}