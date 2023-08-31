package com.seftian.capstoneapp.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.seftian.capstoneapp.ui.screens.viewmodels.MainViewModel
import com.seftian.capstoneapp.data.remote.network.MovieApi
import com.seftian.capstoneapp.domain.ResourceState
import com.seftian.capstoneapp.domain.model.MovieItem
import com.seftian.capstoneapp.ui.Screens
import com.seftian.capstoneapp.ui.screens.components.ImageWithUrl
import com.seftian.capstoneapp.ui.screens.components.Section

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavController,
) {

    val topRatedMovies by viewModel.topRatedMovies.collectAsState()
    val upcomingMovies by viewModel.upcomingMovies.collectAsState()
    val searchedMovies by viewModel.searchedMovies.collectAsState()

    val movieStateTopRated = remember {
        mutableStateOf<List<MovieItem>?>(null)
    }
    val movieStateUpcoming = remember {
        mutableStateOf<List<MovieItem>?>(null)
    }
    val searchedMovieState = remember {
        mutableStateOf<List<MovieItem>?>(null)
    }

    val searchText = remember {
        mutableStateOf("")
    }

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val boxHeight = screenHeight * 0.3f


    processMovieState(movieResourceState = topRatedMovies, movieState = movieStateTopRated)
    processMovieState(movieResourceState = upcomingMovies, movieState = movieStateUpcoming)
    processMovieState(movieResourceState = searchedMovies, movieState = searchedMovieState)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchText.value,
                onValueChange = { searchText.value = it },
                modifier = Modifier.weight(0.7f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { viewModel.searchMovies(searchText.value) },
                modifier = Modifier.weight(0.3f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Search")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (!searchedMovieState.value.isNullOrEmpty() && searchText.value.isNotEmpty()) {
            Section(
                movieList = searchedMovieState.value,
                onClickItem = {
                    navController.navigate(Screens.DetailMovie.withId(it))
                },
                insertMovie = {
                    viewModel.insertMovie(it)
                }
            )
        } else {
            Text("New Movies", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                if (movieStateUpcoming.value.isNullOrEmpty()) {
                    item {
                        Text("Datanya kosong :(")
                    }
                } else {
                    items(movieStateUpcoming.value!!) {
                        AnimatedContent(
                            targetState = it,
                            transitionSpec = {
                                fadeIn(animationSpec = tween(500)) togetherWith
                                        fadeOut(animationSpec = tween(500))
                            }, label = "header game",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(boxHeight)
                                .clickable {
                                    navController.navigate(Screens.DetailMovie.withId(it.id.toString()))
                                }
                        ) {
                            it.title.let { it1 ->
                                ImageWithUrl(
                                    backgroundUrl = "${MovieApi.IMAGE_BASE_URL}${it.posterPath}",
                                    modifier = Modifier.clip(RoundedCornerShape(8.dp))
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Top Rated Movies", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))
            Section(movieList = movieStateTopRated.value, onClickItem = {
                navController.navigate(Screens.DetailMovie.withId(it))
            })
        }
    }
}

@Composable
fun processMovieState(
    movieResourceState: ResourceState<List<MovieItem>>,
    movieState: MutableState<List<MovieItem>?>
): String? {
    when (movieResourceState) {
        is ResourceState.Error -> {
            return movieResourceState.message
        }

        ResourceState.Loading -> {

        }

        is ResourceState.Success -> {
            movieState.value = movieResourceState.data
        }
    }

    return null
}
