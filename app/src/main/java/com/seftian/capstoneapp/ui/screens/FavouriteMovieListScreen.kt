package com.seftian.capstoneapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.seftian.capstoneapp.ui.screens.viewmodels.FavouriteViewModel
import com.seftian.capstoneapp.ui.Screens
import com.seftian.capstoneapp.ui.screens.components.Section

@Composable
fun FavouriteMovieListScreen(
    navController: NavController,
    viewModel: FavouriteViewModel = hiltViewModel()
) {

    val favouriteMovies = viewModel.favouriteMovies.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Section(movieList = favouriteMovies.value, onClickItem = {
            navController.navigate(Screens.DetailGame.withId(it))
        })
    }
}