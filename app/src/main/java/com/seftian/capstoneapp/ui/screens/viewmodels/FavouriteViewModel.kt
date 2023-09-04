package com.seftian.capstoneapp.ui.screens.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seftian.core.domain.model.MovieItem
import com.seftian.core.domain.usecase.MoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel@Inject constructor(
    private val moviesUseCase: MoviesUseCase
): ViewModel() {

    private val _favouriteMovies = MutableStateFlow<List<MovieItem>>(emptyList())
    val favouriteMovies get() = _favouriteMovies

    init {
        viewModelScope.launch {
            moviesUseCase.getFavouriteMovies().collectLatest {
                _favouriteMovies.value = it
            }
        }
    }
}