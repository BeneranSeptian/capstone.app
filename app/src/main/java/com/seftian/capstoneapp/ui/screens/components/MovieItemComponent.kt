package com.seftian.capstoneapp.ui.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.seftian.core.data.remote.network.MovieApi
import com.seftian.core.domain.model.MovieItem

@Composable
fun MovieItemComponent(
    movie: MovieItem,
    modifier: Modifier = Modifier,
    onClickItem: (String) -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .requiredHeight(150.dp)
            .clickable {
                onClickItem(movie.id.toString())
            }
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = "${MovieApi.IMAGE_BASE_URL}${movie.posterPath}",
                contentDescription = "game image",
                contentScale = ContentScale.Fit,
                alignment = Alignment.CenterStart
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = movie.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = if(movie.releaseDate.isNotEmpty()) movie.releaseDate.substring(0,4) else "", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = movie.voteAverage.toString())
            }
        }
    }
}