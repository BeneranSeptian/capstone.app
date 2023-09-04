package com.seftian.capstoneapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.seftian.capstoneapp.ui.Screens
import com.seftian.capstoneapp.ui.screens.components.Section
import com.seftian.capstoneapp.ui.screens.viewmodels.FavouriteViewModel

@Composable
fun FavouriteMovieListScreen(
    navController: NavController,
    viewModel: FavouriteViewModel = hiltViewModel()
) {

    val favouriteMovies = viewModel.favouriteMovies.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
//        Text("wakwaw")
        Section(movieList = favouriteMovies.value, onClickItem = {
            navController.navigate(Screens.DetailMovie.withId(it))
        })
    }
}