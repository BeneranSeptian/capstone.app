package com.seftian.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class MovieItemResponse(
    @SerializedName("genre_ids")
    val genreIds: List<Int>?,
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    val title: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
)