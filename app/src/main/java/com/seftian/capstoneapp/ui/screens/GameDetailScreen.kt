package com.seftian.capstoneapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.seftian.capstoneapp.MainViewModel
import com.seftian.capstoneapp.data.ResourceState

@Composable
fun GameDetailScreen(
    id: String,
    viewModel: MainViewModel = hiltViewModel()
) {

    val gameDetailState by viewModel.gameDetailState.collectAsState()

    when (gameDetailState) {
        is ResourceState.Loading -> {
        }
        is ResourceState.Success -> {
            val game = (gameDetailState as ResourceState.Success).data
        }
        is ResourceState.Error -> {
            val errorMessage = (gameDetailState as ResourceState.Error).message
            Log.d("Game detail screen", errorMessage.toString())
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Button(onClick = { viewModel.getGameDetail(id.toInt()) }) {
            Text(text = "Get detail!")
        }
    }
}