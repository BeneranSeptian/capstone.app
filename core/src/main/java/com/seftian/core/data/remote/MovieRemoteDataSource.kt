package com.seftian.core.data.remote

import com.seftian.core.data.remote.network.MovieApi
import com.seftian.core.data.remote.network.ResponseStatus
import com.seftian.core.data.remote.response.MovieDetailResponse
import com.seftian.core.data.remote.response.MovieItemResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieRemoteDataSource(private val movieApi: MovieApi) {

    fun getMoviesFromNetwork(category: String):Flow<ResponseStatus<List<MovieItemResponse>>>{
        return safeApiCall { movieApi.getMovies(category).results }.flowOn(Dispatchers.IO)
    }

    fun searchMoviesFromNetwork(query: String): Flow<ResponseStatus<List<MovieItemResponse>>>{
        return safeApiCall { movieApi.searchMovies(query).results }.flowOn(Dispatchers.IO)
    }

    fun getDetailMovieFromNetwork(id: Int): Flow<ResponseStatus<MovieDetailResponse>>{
        return safeApiCall { movieApi.getDetailMovie(id) }.flowOn(Dispatchers.IO)
    }

    private fun <T : Any> safeApiCall(
        apiCall: suspend () -> T
    ): Flow<ResponseStatus<T>> = flow {
        try {
            val response = apiCall()
            emit(ResponseStatus.Success(response))
        } catch (e: Exception) {
            emit(ResponseStatus.Error(e.message ?: "An error occurred"))
        }
    }
}