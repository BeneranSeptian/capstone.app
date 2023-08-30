package com.seftian.capstoneapp.ui.screens.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seftian.capstoneapp.domain.ResourceState
import com.seftian.capstoneapp.domain.model.MovieDetail
import com.seftian.capstoneapp.domain.usecase.MoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val moviesUseCase: MoviesUseCase,
): ViewModel() {

    private val _movieDetail = MutableStateFlow<ResourceState<MovieDetail>>(ResourceState.Loading)
    val movieDetail get() = _movieDetail

    suspend fun getMovieDetail(id: Int){
        moviesUseCase.getMovieDetail(id).collectLatest {
            _movieDetail.value = it
        }
    }

    fun addMovieToFavourite(id: Int, isFavourite: Boolean){
        viewModelScope.launch {
            moviesUseCase.addMovieToFavourite(id, isFavourite)
            getMovieDetail(id)
        }
    }
}