package com.example.spectrum_themoviedb_test.ui.movieDetail

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Visibility
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.spectrum_themoviedb_test.R
import com.example.spectrum_themoviedb_test.data.model.UiMovieDetail
import com.example.spectrum_themoviedb_test.util.Constants.BASE_IMAGE_PATH
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MovieDetailScreen(
    movieId: Int,
    popBackStack: () -> Unit,
    movieDetailVM: MovieDetailVM = hiltViewModel()
) {

    val scrollState = rememberScrollState()
    val contextForToast = LocalContext.current.applicationContext

    val state by movieDetailVM.movieDetailState.collectAsStateWithLifecycle()

    var bookmarkButtonState = state.movieDetail.bookmarked

    LaunchedEffect(true) {
        movieDetailVM.getMovie(movieId)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF161520))
    ) {
        state.movieDetail.let { movie ->
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                val (backDrop, poster, bookmark, title, tagLine, rating, ratingIcon, genres, overview, status, languages) = createRefs()

                AsyncImage(
                    model = BASE_IMAGE_PATH + movie.backDropPath,
                    error = painterResource(id = R.drawable.the_movie_db_default_backdrop),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .constrainAs(backDrop) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop,
                    contentDescription = "Movie Backdrop"
                )
                AsyncImage(
                    model = BASE_IMAGE_PATH + movie.posterPath,
                    contentDescription = "Movie Poster",
                    placeholder = painterResource(id = R.drawable.the_movie_db_default_poster),
                    error = painterResource(id = R.drawable.the_movie_db_default_poster),
                    modifier = Modifier
                        .size(96.dp)
                        .clip(CircleShape)
                        .constrainAs(poster) {
                            top.linkTo(backDrop.bottom, margin = (-32).dp)
                            start.linkTo(backDrop.start)
                            end.linkTo(backDrop.end)
                            bottom.linkTo(backDrop.bottom)
                        },
                    contentScale = ContentScale.Crop
                )

                val bookmarkError = stringResource(id = R.string.bookmark_error)
                IconButton(
                    onClick = {
                        if (state.throwable != null && state.movieDetail == UiMovieDetail.EMPTY) {
                            Toast.makeText(
                                contextForToast,
                                bookmarkError,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            bookmarkButtonState = !bookmarkButtonState
                            movieDetailVM.setBookmarkState(movie, bookmarkButtonState)
                        }

                    },
                    modifier = Modifier
                        .constrainAs(bookmark) {
                            top.linkTo(backDrop.top, margin = 16.dp)
                            end.linkTo(backDrop.end, margin = 16.dp)
                        },
                ) {
                    Icon(
                        imageVector = if (movie.bookmarked) {
                            Icons.Filled.Favorite
                        } else {
                            Icons.Outlined.Favorite
                        },
                        tint = if (movie.bookmarked) Color.Red else Color.White,
                        modifier = Modifier.size(36.dp),
                        contentDescription = null)
                }


                movie.title?.let {
                    Text(
                        text = it,
                        fontSize = 24.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontStyle = MaterialTheme.typography.h2.fontStyle,
                        fontFamily = MaterialTheme.typography.h2.fontFamily,
                        modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight()
                            .padding(bottom = 8.dp, top = 8.dp)
                            .constrainAs(title) {
                                top.linkTo(poster.bottom, margin = 10.dp)
                                start.linkTo(backDrop.start, margin = 10.dp)
                                visibility = if (it.isEmpty()) {
                                    Visibility.Gone
                                } else {
                                    Visibility.Visible
                                }
                            }
                    )
                }

                movie.tagLine?.let {
                    Text(
                        text = it,
                        style = TextStyle(
                            color = Color.DarkGray,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = MaterialTheme.typography.h6.fontFamily,
                            lineHeight = 18.sp,
                            textAlign = TextAlign.Left
                        ),
                        modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight()
                            .padding(start = 4.dp, end = 4.dp)
                            .clickable { }
                            .padding(start = 8.dp, end = 8.dp)
                            .constrainAs(tagLine) {
                                top.linkTo(title.bottom, margin = 8.dp)
                                start.linkTo(backDrop.start)
                                visibility = if (it.isEmpty()) {
                                    Visibility.Gone
                                } else {
                                    Visibility.Visible
                                }
                            }
                    )
                }


                Icon(
                    imageVector = Icons.Filled.Star,
                    tint = Color.Yellow,
                    modifier = Modifier
                        .size(36.dp)
                        .padding(bottom = 8.dp, top = 8.dp)
                        .constrainAs(ratingIcon) {
                            top.linkTo(tagLine.bottom, margin = 8.dp)
                            start.linkTo(backDrop.start, margin = 4.dp)
                            visibility = if (movie.voteCount == null && movie.voteAverage == null) {
                                Visibility.Gone
                            } else {
                                Visibility.Visible
                            }
                        },
                    contentDescription = null
                )

                if (movie.voteCount != null && movie.voteAverage != null){
                    Text(
                        text = if (movie.voteCount!! <= 0) {
                            buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        color = Color.White,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium,
                                        fontFamily = MaterialTheme.typography.h6.fontFamily
                                    )
                                ) {
                                    append(stringResource(id = R.string.no_rating))
                                }
                            }
                        } else {
                            buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        color = Color.White,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = MaterialTheme.typography.h6.fontFamily
                                    )
                                ) {
                                    append(getValue(movie.voteAverage!!))
                                }
                                withStyle(
                                    style = SpanStyle(
                                        color = Color.White,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium,
                                        fontFamily = MaterialTheme.typography.h6.fontFamily
                                    )
                                ) {
                                    append("/10")
                                }

                                withStyle(
                                    style = SpanStyle(
                                        color = Color.White,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium,
                                        fontFamily = MaterialTheme.typography.h6.fontFamily
                                    )
                                ) {
                                    append(" from ")
                                }

                                withStyle(
                                    style = SpanStyle(
                                        color = Color.White,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = MaterialTheme.typography.h6.fontFamily
                                    )
                                ) {
                                    append(movie.voteCount.toString())
                                }
                                withStyle(
                                    style = SpanStyle(
                                        color = Color.White,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium,
                                        fontFamily = MaterialTheme.typography.h6.fontFamily
                                    )
                                ) {
                                    append(" users")
                                }
                            }
                        },
                        modifier = Modifier
                            .padding(bottom = 8.dp, top = 8.dp)
                            .constrainAs(rating) {
                                top.linkTo(tagLine.bottom, margin = 8.dp)
                                start.linkTo(ratingIcon.end, margin = (-4).dp)

                            }

                    )
                }

                FlowRow(
                    maxItemsInEachRow = 3,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp, top = 8.dp)
                        .constrainAs(genres) {
                            top.linkTo(ratingIcon.bottom, margin = 8.dp)
                            start.linkTo(ratingIcon.start, margin = 8.dp)
                            visibility = if (movie.genre.isNullOrEmpty()) {
                                Visibility.Gone
                            } else {
                                Visibility.Visible
                            }
                        }
                ) {
                    movie.genre?.let { list ->
                        repeat(list.size) { size ->
                            Box(
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .wrapContentHeight()
                                    .padding(end = 8.dp, bottom = 8.dp)
                                    .clip(RoundedCornerShape(24.dp))
                                    .background(Color.DarkGray)
                                    .clickable { }
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = list[size],
                                    style = TextStyle(
                                        color = Color.White,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        fontFamily = MaterialTheme.typography.h4.fontFamily,
                                        textAlign = TextAlign.Left
                                    ),
                                    modifier = Modifier
                                        .wrapContentWidth()
                                        .wrapContentHeight()
                                        .padding(
                                            start = 8.dp,
                                            end = 8.dp,
                                            top = 2.dp,
                                            bottom = 2.dp
                                        )
                                )
                            }
                        }
                    }
                }

                movie.overview?.let {
                    Text(
                        text = it,
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = MaterialTheme.typography.h6.fontFamily,
                            textAlign = TextAlign.Left,
                            lineHeight = 24.sp
                        ),
                        modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight()
                            .padding(start = 4.dp, end = 4.dp)
                            .clickable { }
                            .padding(start = 8.dp, end = 8.dp)
                            .constrainAs(overview) {
                                top.linkTo(genres.bottom, margin = 8.dp)
                                start.linkTo(backDrop.start, margin = 2.dp)
                                visibility = if (it.isEmpty()) {
                                    Visibility.Gone
                                } else {
                                    Visibility.Visible
                                }
                            }
                    )
                }

                if (movie.status != null && movie.releaseDate != null){
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    fontStyle = FontStyle.Italic,
                                    fontFamily = MaterialTheme.typography.h6.fontFamily
                                )
                            ) {
                                append("${movie.status} on : ")
                            }
                            withStyle(
                                style = SpanStyle(
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    fontStyle = FontStyle.Italic,
                                    fontFamily = MaterialTheme.typography.h6.fontFamily
                                )
                            ) {
                                append(
                                    movie.releaseDate!!.ifEmpty {
                                        "N/A"
                                    }
                                )
                            }
                        },
                        modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight()
                            .padding(start = 4.dp, end = 4.dp)
                            .clickable { }
                            .padding(start = 8.dp, end = 8.dp)
                            .constrainAs(status) {
                                top.linkTo(overview.bottom, margin = 12.dp)
                                start.linkTo(backDrop.start)
                            }
                    )
                }

                movie.spokenLanguages?.let { languagesAvailable ->
                    val separator = ", "
                    val movieLanguages =
                        if (languagesAvailable.isEmpty()) "N/A" else languagesAvailable.joinToString(
                            separator = separator
                        )
                    Text(
                        text = "Languages: $movieLanguages",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            fontStyle = FontStyle.Italic,
                            fontFamily = MaterialTheme.typography.h6.fontFamily,
                            textAlign = TextAlign.Left
                        ),
                        modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight()
                            .padding(bottom = 8.dp, top = 8.dp)
                            .constrainAs(languages) {
                                top.linkTo(status.bottom, margin = 8.dp)
                                start.linkTo(backDrop.start, margin = 12.dp)
                                bottom.linkTo(parent.bottom, margin = 12.dp)
                            }
                    )
                }

            }

        }

        MovieDetailScreenState()
    }
}

private fun getValue(doubleValue: Double): String {
    return String.format(Locale.US, "%.1f", doubleValue)
}