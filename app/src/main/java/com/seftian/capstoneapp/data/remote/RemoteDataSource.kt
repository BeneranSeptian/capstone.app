package com.seftian.capstoneapp.data.remote

import com.seftian.capstoneapp.data.remote.network.Api
import com.seftian.capstoneapp.data.remote.network.ResponseStatus
import com.seftian.capstoneapp.data.remote.response.GameDetailResponse
import com.seftian.capstoneapp.data.remote.response.GameItemResponse
import com.seftian.capstoneapp.domain.model.GameDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class RemoteDataSource(private val api: Api){

    fun getGames(): Flow<ResponseStatus<List<GameItemResponse>>> {
        return safeApiCall { api.getGames().results }.flowOn(Dispatchers.IO)
    }

    fun getGameDetail(id: Int): Flow<ResponseStatus<GameDetailResponse>> {
        return safeApiCall { api.getGameDetail(id) }.flowOn(Dispatchers.IO)
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