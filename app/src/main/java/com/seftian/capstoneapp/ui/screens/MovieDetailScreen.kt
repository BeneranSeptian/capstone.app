package com.seftian.capstoneapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.seftian.capstoneapp.ui.screens.viewmodels.DetailViewModel
import com.seftian.capstoneapp.ui.screens.components.ImageWithUrl
import com.seftian.core.data.remote.network.MovieApi
import com.seftian.core.domain.ResourceState
import com.seftian.core.domain.model.MovieDetail

@Composable
fun MovieDetailScreen(
    id: String,
    viewModel: DetailViewModel = hiltViewModel()
) {

    val detailMovieResource = viewModel.movieDetail.collectAsState()

    var detailMovieState by remember {
        mutableStateOf<MovieDetail?>(null)
    }

    val context = LocalContext.current

    LaunchedEffect(key1 = id) {
        viewModel.getMovieDetail(id.toInt())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when (val currentState = detailMovieResource.value) {
            ResourceState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            is ResourceState.Error -> {
                Toast.makeText(context, "Failed to load data", Toast.LENGTH_SHORT).show()
            }

            is ResourceState.Success -> {
                detailMovieState = currentState.data

                DetailMovieContent(
                    movieDetail = detailMovieState!!,
                    onClickFavourite = { id, isFavourite ->
                        viewModel.addMovieToFavourite(id, isFavourite)
                    })

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun DetailMovieContent(
    modifier: Modifier = Modifier,
    movieDetail: MovieDetail,
    onClickFavourite: (id: Int, isFavourite: Boolean) -> Unit
) {

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val boxHeight = screenHeight * 0.4f

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ImageWithUrl(
                backgroundUrl = "${MovieApi.IMAGE_BASE_URL}${movieDetail.posterPath}",
                modifier = Modifier.height(boxHeight)
            )

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                movieDetail.genres?.forEach { item ->
                    SuggestionChip(onClick = { }, label = {
                        Text(item.name)
                    })
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "${movieDetail.title}(${movieDetail.releaseDate.substring(0, 4)})",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(movieDetail.overview)
        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            onClick = {
                onClickFavourite(movieDetail.id, !movieDetail.isFavourite)
            }
        ) {
            Icon(
                imageVector = if (movieDetail.isFavourite) Icons.Outlined.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = null
            )
        }
    }
}
