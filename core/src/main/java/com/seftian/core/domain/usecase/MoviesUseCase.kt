package com.seftian.core.domain.usecase

import com.seftian.core.domain.ResourceState
import com.seftian.core.domain.model.MovieDetail
import com.seftian.core.domain.model.MovieItem
import kotlinx.coroutines.flow.Flow

interface MoviesUseCase {
    fun getMovies(category: String): Flow<ResourceState<List<MovieItem>>>
    fun searchMovies(query: String): Flow<ResourceState<List<MovieItem>>>
    fun getMovieDetail(id: Int): Flow<ResourceState<MovieDetail>>
    suspend fun addMovieToFavourite(id:Int, isFavourite: Boolean)

    fun getFavouriteMovies(): Flow<List<MovieItem>>
    suspend fun insertMovie(movie: MovieItem)
}