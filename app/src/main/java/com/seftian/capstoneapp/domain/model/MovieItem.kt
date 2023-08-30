package com.seftian.capstoneapp.domain.model

data class MovieItem(
    val genreIds: List<Int>,
    val id: Int,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
)
