package com.seftian.core.data.remote.network

import com.seftian.core.data.remote.response.MovieDetailResponse
import com.seftian.core.data.remote.response.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/{category}")
    suspend fun getMovies(@Path("category")category: String): MoviesResponse

    @GET("search/movie")
    suspend fun searchMovies(@Query("query") query: String): MoviesResponse

    @GET("movie/{id}")
    suspend fun getDetailMovie(@Path("id")id: Int): MovieDetailResponse

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w780/"
    }
}