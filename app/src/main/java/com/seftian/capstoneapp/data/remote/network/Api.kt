package com.seftian.capstoneapp.data.remote.network

import com.seftian.capstoneapp.data.remote.response.GameDetailResponse
import com.seftian.capstoneapp.data.remote.response.GamesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("games")
    suspend fun getGames(): GamesResponse

    @GET("games/{id}")
    suspend fun getGameDetail(@Path("id") id: Int): GameDetailResponse

    companion object {
        const val BASE_URL = "https://api.rawg.io/api/"
    }
}