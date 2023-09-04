package com.seftian.core.data

import android.util.Log
import com.seftian.core.data.local.LocalDataSource
import com.seftian.core.data.local.entity.MoviesEntity
import com.seftian.core.data.remote.MovieRemoteDataSource
import com.seftian.core.data.remote.network.ResponseStatus
import com.seftian.core.data.remote.response.MovieItemResponse
import com.seftian.core.domain.ResourceState
import com.seftian.core.domain.model.MovieDetail
import com.seftian.core.domain.model.MovieItem
import com.seftian.core.domain.repository.IMoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val localDataSource: LocalDataSource,
) : IMoviesRepository {
    override fun getMovies(category: String): Flow<ResourceState<List<MovieItem>>> {
        return flow {
            emit(ResourceState.Loading)

            fun mapEntityToItem(entity: MoviesEntity): MovieItem {
                return MovieItem(
                    id = entity.id,
                    title = entity.title,
                    posterPath = entity.posterPath,
                    genreIds = entity.genres?.map { it.id } ?: emptyList(),
                    releaseDate = entity.releaseDate,
                    voteAverage = entity.voteAverage
                )
            }

            val localMovies = localDataSource.getAllMovies(category).first()

            if (localMovies.isNotEmpty()) {
                val movieItems = localMovies.map(::mapEntityToItem)
                emit(ResourceState.Success(movieItems))
                return@flow
            }


            when (val response = movieRemoteDataSource.getMoviesFromNetwork(category).last()) {
                is ResponseStatus.Success -> {
                    val data = response.data

                    val movieItemList = data.mapNotNull(::movieResponseToMovieItem)

                    movieItemList.forEach { movieItem ->
                        val movieEntity = MoviesEntity(
                            id = movieItem.id,
                            title = movieItem.title,
                            voteAverage = movieItem.voteAverage,
                            releaseDate = movieItem.releaseDate,
                            posterPath = movieItem.posterPath,
                            overview = null,
                            genres = null,
                            isFavourite = false,
                            tag = category
                        )
                        localDataSource.insertMovie(movieEntity)
                    }

                    emit(ResourceState.Success(movieItemList))
                }

                is ResponseStatus.Error -> {
                    emit(ResourceState.Error(response.message))
                }
            }
        }.flowOn(Dispatchers.IO)
    }


    override fun searchMovies(query: String): Flow<ResourceState<List<MovieItem>>> {
        return flow {
            emit(ResourceState.Loading)
            val response = movieRemoteDataSource.searchMoviesFromNetwork(query).last()
            emit(handleResponse(response) { data ->
                data.mapNotNull(::movieResponseToMovieItem)
            })
        }
    }

    override fun getMovieDetail(id: Int): Flow<ResourceState<MovieDetail>> {
        return flow {
            emit(ResourceState.Loading)

            val localDetail = localDataSource.getMovieBy(id).firstOrNull()

            if (localDetail?.overview != null && localDetail.genres != null) {
                val movieDetail = MovieDetail(
                    id = localDetail.id,
                    title = localDetail.title,
                    posterPath = localDetail.posterPath,
                    releaseDate = localDetail.releaseDate,
                    overview = localDetail.overview,
                    genres = localDetail.genres,
                    isFavourite = localDetail.isFavourite
                )
                emit(ResourceState.Success(movieDetail))
            } else {
                when (val response = movieRemoteDataSource.getDetailMovieFromNetwork(id).last()) {
                    is ResponseStatus.Success -> {
                        val data = response.data
                        val movieDetail = MovieDetail(
                            id = data.id,
                            title = data.title!!,
                            posterPath = data.posterPath!!,
                            genres = data.genres,
                            overview = data.overview!!,
                            releaseDate = data.releaseDate!!,
                            isFavourite = false,
                        )

                        localDataSource.updateMovie(
                            id= movieDetail.id,
                            overview = movieDetail.overview,
                            genres = movieDetail.genres!!
                        )

                        emit(ResourceState.Success(movieDetail))
                    }
                    is ResponseStatus.Error -> {
                        emit(ResourceState.Error(response.message))
                    }
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun addMovieToFavourite(id: Int, isFavourite: Boolean) {
        withContext(Dispatchers.IO){
            localDataSource.addMovieToFavourite(id, isFavourite)
        }
    }

    override fun getFavouriteMovies(): Flow<List<MovieItem>> {

        return flow {
            val movieItems = localDataSource.getFavouriteMovies().first().map{
                MovieItem(
                    id = it.id,
                    title = it.title,
                    posterPath = it.posterPath,
                    genreIds = it.genres?.map { it.id } ?: emptyList(),
                    releaseDate = it.releaseDate,
                    voteAverage = it.voteAverage
                )
            }
            emit(movieItems)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun insertMovie(movie: MovieItem) {
        withContext(Dispatchers.IO){
            val moviesEntity = MoviesEntity(
                id = movie.id,
                title = movie.title,
                voteAverage = movie.voteAverage,
                releaseDate = movie.releaseDate,
                posterPath = movie.posterPath,
                overview = null,
                genres = null,
                isFavourite = false,
                tag = "search"
            )
            localDataSource.insertMovie(moviesEntity)
        }
    }

    private inline fun <T : Any, R : Any> handleResponse(response: ResponseStatus<T>, transform: (T) -> R): ResourceState<R> {
        return when (response) {
            is ResponseStatus.Success -> {
                val transformedData = transform(response.data)
                ResourceState.Success(transformedData)
            }
            is ResponseStatus.Error -> ResourceState.Error(response.message)
        }
    }

    private fun movieResponseToMovieItem(movieResponse: MovieItemResponse): MovieItem? {
        return if (
            movieResponse.title != null &&
            movieResponse.releaseDate != null &&
            movieResponse.posterPath != null &&
            movieResponse.genreIds != null &&
            movieResponse.voteAverage != null
        ) {
            MovieItem(
                id = movieResponse.id,
                title = movieResponse.title,
                posterPath = movieResponse.posterPath,
                genreIds = movieResponse.genreIds,
                releaseDate = movieResponse.releaseDate,
                voteAverage = movieResponse.voteAverage
            )
        } else {
            null
        }
    }
}
