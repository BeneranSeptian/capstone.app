package com.seftian.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.seftian.core.data.local.entity.MoviesEntity
import com.seftian.core.data.remote.response.Genre
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovies(movie: MoviesEntity)

    @Query("UPDATE movies SET overview = :overview, genres = :genres WHERE id = :id")
    fun updateMovie(overview: String, genres: List<Genre>, id: Int,)

    @Query("SELECT * FROM movies WHERE tag = :category")
    fun getAllMoviesFromLocal(category: String): Flow<List<MoviesEntity>>

    @Query("SELECT * from movies WHERE id = :id")
    fun getSingleMovieBy(id: Int): Flow<MoviesEntity>

    @Query("UPDATE movies SET isFavourite = :isFavourite WHERE id = :id")
    fun addMovieToFavourite(isFavourite: Boolean, id: Int,)

    @Query("SELECT * from movies WHERE isFavourite = 1")
    fun getFavouriteMovies(): Flow<List<MoviesEntity>>
}