package com.seftian.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.seftian.core.data.remote.response.Genre

@Entity(
    tableName = "movies"
)
data class MoviesEntity(
    @PrimaryKey
    val id: Int,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val overview: String?,
    val genres: List<Genre>?,
    val voteAverage: Double,
    val isFavourite: Boolean = false,
    val tag: String,
)
