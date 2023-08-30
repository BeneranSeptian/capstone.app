package com.seftian.capstoneapp.ui.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seftian.capstoneapp.domain.model.MovieItem

@Composable
fun Section(
    movieList: List<MovieItem>?,
    onClickItem: (String) -> Unit,
    insertMovie: (MovieItem) -> Unit = {}
) {

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        if (movieList.isNullOrEmpty()) {
            item {
                Text("datanya kosong :(")
            }
        } else {
            items(movieList) {movieItem ->
                MovieItemComponent(
                    movie = movieItem,
                    modifier = Modifier.fillParentMaxWidth(),
                    onClickItem = {
                        onClickItem(it)
                        insertMovie(movieItem)
                    }
                )
            }
        }

    }
}