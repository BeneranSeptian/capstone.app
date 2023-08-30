package com.seftian.capstoneapp.ui.screens.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seftian.capstoneapp.domain.ResourceState
import com.seftian.capstoneapp.domain.model.MovieItem
import com.seftian.capstoneapp.domain.usecase.MoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val moviesUseCase: MoviesUseCase
) : ViewModel() {


    private val _topRatedMovies = MutableStateFlow<ResourceState<List<MovieItem>>>(ResourceState.Loading)
    val topRatedMovies get() = _topRatedMovies

    private val _upcomingMovies = MutableStateFlow<ResourceState<List<MovieItem>>>(ResourceState.Loading)
    val upcomingMovies get() = _upcomingMovies

    private val _searchedMovies = MutableStateFlow<ResourceState<List<MovieItem>>>(ResourceState.Loading)
    val searchedMovies get() = _searchedMovies

    init {
        viewModelScope.launch {
            moviesUseCase.getMovies("top_rated").collectLatest {
                _topRatedMovies.value = it
            }

            moviesUseCase.getMovies("upcoming").collectLatest {
                _upcomingMovies.value = it
            }
        }
    }


    fun searchMovies(query: String){
        viewModelScope.launch {
            moviesUseCase.searchMovies(query).collectLatest {
                _searchedMovies.value = it
            }
        }
    }

    fun insertMovie(movie: MovieItem){
        viewModelScope.launch {
            moviesUseCase.insertMovie(movie)
        }
    }
}