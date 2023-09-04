package com.seftian.core.domain.usecase

import android.util.Log
import com.seftian.core.domain.ResourceState
import com.seftian.core.domain.model.MovieDetail
import com.seftian.core.domain.model.MovieItem
import kotlinx.coroutines.flow.Flow

class MoviesInteractor(private val moviesRepository: com.seftian.core.data.MoviesRepository): MoviesUseCase {
    override fun getMovies(category: String): Flow<ResourceState<List<MovieItem>>> {
        Log.d("interactor", "dijalanin")
        return moviesRepository.getMovies(category)
    }

    override fun searchMovies(query: String): Flow<ResourceState<List<MovieItem>>> {
        return moviesRepository.searchMovies(query)
    }

    override fun getMovieDetail(id: Int): Flow<ResourceState<MovieDetail>> {
        return moviesRepository.getMovieDetail(id)
    }

    override suspend fun addMovieToFavourite(id: Int, isFavourite: Boolean) {
        moviesRepository.addMovieToFavourite(id, isFavourite)
    }

    override fun getFavouriteMovies(): Flow<List<MovieItem>> {
        return moviesRepository.getFavouriteMovies()
    }

    override suspend fun insertMovie(movie: MovieItem) {
        moviesRepository.insertMovie(movie = movie)
    }
}