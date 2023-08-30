package com.seftian.capstoneapp.data.local

import com.seftian.capstoneapp.data.local.dao.MoviesDao
import com.seftian.capstoneapp.data.local.entity.MoviesEntity
import com.seftian.capstoneapp.data.remote.response.Genre
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val moviesDao: MoviesDao) {

    fun getAllMovies(category: String): Flow<List<MoviesEntity>> = moviesDao.getAllMoviesFromLocal(category)

    fun insertMovie(movie: MoviesEntity){
        moviesDao.insertMovies(movie)
    }

    fun getMovieBy(id: Int): Flow<MoviesEntity>{
        return moviesDao.getSingleMovieBy(id)
    }

    fun updateMovie(overview: String, genres: List<Genre>, id: Int,){
        moviesDao.updateMovie(overview, genres, id)
    }

    fun addMovieToFavourite(id: Int, isFavourite: Boolean){
        moviesDao.addMovieToFavourite(id = id, isFavourite = isFavourite)
    }

    fun getFavouriteMovies(): Flow<List<MoviesEntity>> = moviesDao.getFavouriteMovies()
}