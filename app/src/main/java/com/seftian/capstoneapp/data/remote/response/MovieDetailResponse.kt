package com.seftian.capstoneapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    val title: String?,
    val overview: String?,
    val genres: List<Genre>?
)

data class Genre(
    val id: Int,
    val name: String
)