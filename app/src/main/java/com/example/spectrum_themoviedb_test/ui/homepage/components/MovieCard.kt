package com.example.spectrum_themoviedb_test.ui.homepage.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Visibility
import coil.compose.AsyncImage
import com.example.spectrum_themoviedb_test.R
import com.example.spectrum_themoviedb_test.data.model.UiMovieItem
import com.example.spectrum_themoviedb_test.util.Constants.BASE_IMAGE_PATH
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MovieCard(
    movieItem: UiMovieItem,
    onClick: (movieId: Int) -> Unit
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .clickable { onClick(movieItem.movieId) }
    ) {
        ConstraintLayout {

            val (poster, averageCount, averageViews, releasedDateText, movieGenres, movieTitle) = createRefs()

            AsyncImage(
                model = BASE_IMAGE_PATH + movieItem.posterPath,
                modifier = Modifier
                    .padding(0.5.dp)
                    .fillMaxWidth()
                    .constrainAs(poster) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .height(150.dp),
                placeholder = painterResource(id = R.drawable.the_movie_db_default_poster),
                error = painterResource(id = R.drawable.the_movie_db_default_poster),
                contentScale = ContentScale.Crop,
                contentDescription = "film poster"
            )

            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .padding(top = 4.dp, end = 4.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(R.color.release_date_background))
                    .clickable { }
                    .padding(4.dp)
                    .constrainAs(releasedDateText) {
                        top.linkTo(poster.top)
                        end.linkTo(poster.end)
                        visibility = if (movieItem.releaseDate.isEmpty()) {
                            Visibility.Gone
                        } else {
                            Visibility.Visible
                        }
                    }
            ) {
                Text(
                    text = movieItem.releaseDate,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = MaterialTheme.typography.h4.fontFamily,
                    ),
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Text(
                text = if (movieItem.voteAverage <= 0.0) stringResource(id = R.string.not_available) else
                    "${getValue(movieItem.voteAverage)}/10",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = MaterialTheme.typography.h4.fontFamily
                ),
                modifier = Modifier
                    .padding(4.dp, 2.dp, 4.dp, 2.dp)
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .clickable { }
                    .padding(bottom = 4.dp, start = 4.dp)
                    .constrainAs(averageCount) {
                        bottom.linkTo(poster.bottom)
                        start.linkTo(poster.start)
                    }
            )



            Text(
                text = pluralStringResource(id = R.plurals.vote_count, count = movieItem.voteCount, movieItem.voteCount),
                style = TextStyle(
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = MaterialTheme.typography.h4.fontFamily
                ),
                modifier = Modifier
                    .padding(4.dp, 2.dp, 4.dp, 2.dp)
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .clickable { }
                    .padding(bottom = 4.dp, end = 4.dp)
                    .constrainAs(averageViews) {
                        bottom.linkTo(poster.bottom)
                        end.linkTo(poster.end)
                    }
            )



            FlowRow(
                maxItemsInEachRow = 3,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .padding(bottom = 8.dp, top = 8.dp)
                    .constrainAs(movieGenres) {
                        top.linkTo(poster.bottom)
                        start.linkTo(poster.start)
                        end.linkTo(poster.end)
                        visibility = if (movieItem.genre.isEmpty()) {
                            Visibility.Gone
                        } else {
                            Visibility.Visible
                        }
                    }

            ) {
                repeat(movieItem.genre.size) {
                    Text(
                        text = movieItem.genre[it],
                        style = TextStyle(
                            color = Color.DarkGray,
                            fontSize = 12.sp,
                            fontFamily = MaterialTheme.typography.h6.fontFamily,
                            textAlign = TextAlign.Left
                        ),
                        modifier = Modifier
                            .padding(4.dp, 2.dp, 4.dp, 2.dp)
                            .wrapContentWidth()
                            .wrapContentHeight()
                            .clickable { }
                            .padding(top = 4.dp, end = 4.dp)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 10.dp)
                    .clip(RoundedCornerShape(bottomEnd = 10.dp, bottomStart = 10.dp))
                    .background(Color(R.color.title_background))
                    .clickable { }
                    .padding(bottom = 8.dp, top = 8.dp, start = 16.dp, end = 16.dp)
                    .constrainAs(movieTitle) {
                        top.linkTo(movieGenres.bottom)
                        start.linkTo(movieGenres.start)
                        end.linkTo(movieGenres.end)
                        bottom.linkTo(parent.bottom)
                    }
            ) {
                Text(
                    text = movieItem.title,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = MaterialTheme.typography.h4.fontFamily,
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

private fun getValue(doubleValue: Double): String {
    return String.format(Locale.US, "%.1f", doubleValue)
}