package com.example.cinema_ticketing_app.home.presentation

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.layout.lerp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cinema_ticketing_app.core.route.AppRouteName
import com.example.cinema_ticketing_app.core.theme.BlueVariant
import com.example.cinema_ticketing_app.core.theme.Yellow
import com.example.cinema_ticketing_app.home.model.MovieModel
import com.example.cinema_ticketing_app.home.model.nowPlayingMovie
import com.example.cinema_ticketing_app.home.model.upcoming
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import kotlin.math.absoluteValue

@Composable
fun HomeScreen(
    navController: NavHostController,
) {
    val scrollState = rememberScrollState()

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(
                    top = padding.calculateTopPadding() + 24.dp,
                    bottom = padding.calculateBottomPadding() + 24.dp,
                )
        ) {
            Text(
                text = "Welcome back, Akmal!",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Book your Favorite Movie Here!",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            //Banners()
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Text(
                    text = "Category",
                    style = MaterialTheme.typography.h6,
                )
                TextButton(onClick = { }) {
                    Text(text = "See All")
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            //Categories()
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Text(
                    text = "Now Playing Movie",
                    style = MaterialTheme.typography.h6,
                )
                TextButton(onClick = { }) {
                    Text(text = "See All")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            NowPlayingMovie { movie ->
                //navController.navigate("${AppRouteName.Detail}/${movie.id}")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Text(
                    text = "Upcoming Movie",
                    style = MaterialTheme.typography.h6,
                )
                TextButton(onClick = { }) {
                    Text(text = "See All")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            UpcomingMovie()
        }
    }
}

    @Composable
    fun UpcomingMovie() {
        LazyRow(
            contentPadding = PaddingValues(start = 24.dp)
        ) {
            items(count = upcoming.size) { index ->
                Box(modifier = Modifier
                    .padding(end = 24.dp)
                    .clickable { }) {
                    Column(
                        modifier = Modifier.wrapContentHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = upcoming[index].assetImage),
                            contentDescription = "Movie Image",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.size(width = 200.dp, height = 260.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = upcoming[index].title,
                            style = MaterialTheme.typography.subtitle1,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }

@OptIn(ExperimentalPagerApi::class)
@Composable
fun NowPlayingMovie(
    onMovieClicked: (MovieModel) -> Unit
) {
    HorizontalPager(
        count = nowPlayingMovie.size,
        contentPadding = PaddingValues(start = 48.dp, end = 48.dp)
    ) { page ->

        Column(
            modifier = Modifier
                .wrapContentHeight()
                .graphicsLayer {
                    val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                    lerp(
                        start = ScaleFactor(1f, 0.85f),
                        stop = ScaleFactor(1f, 1f),
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    ).also { scale ->
                        scaleX = scale.scaleX
                        scaleY = scale.scaleY
                    }
                }
                .clickable {
                    onMovieClicked(nowPlayingMovie[page])
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.clip(RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.BottomCenter

            ) {
                Image(
                    painter = painterResource(id = nowPlayingMovie[page].assetImage),
                    contentDescription = "Movie Image",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.85f)
                        .height(340.dp)
                )
                Box(
                    modifier = Modifier
                        .graphicsLayer {
                            val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                            val translation = pageOffset.coerceIn(0f, 1f)

                            translationY = translation * 200
                        }
                        .fillMaxWidth(fraction = 0.85f)
                        .wrapContentHeight()
                        .background(
                            BlueVariant
                        )
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Buy Ticket", style = MaterialTheme.typography.subtitle1.copy(
                            color = Yellow,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = nowPlayingMovie[page].title,
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}



