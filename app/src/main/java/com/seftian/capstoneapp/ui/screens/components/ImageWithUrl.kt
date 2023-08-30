package com.seftian.capstoneapp.ui.screens.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.SubcomposeAsyncImage

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Composable
fun ImageWithUrl(
    backgroundUrl: String,
    modifier: Modifier = Modifier,

) {
    Box(
        modifier = modifier
            .fillMaxSize()
    )
    {
        SubcomposeAsyncImage(
            model = backgroundUrl,
            contentDescription = "game image",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize(),
            loading = {
                CircularProgressIndicator()
            }
        )
    }
}