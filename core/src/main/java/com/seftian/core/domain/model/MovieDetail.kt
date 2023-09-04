package com.seftian.core.domain.model

import com.seftian.core.data.remote.response.Genre

data class MovieDetail(
    val id: Int,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val overview: String,
    val genres: List<Genre>?,
    val isFavourite: Boolean,
)
