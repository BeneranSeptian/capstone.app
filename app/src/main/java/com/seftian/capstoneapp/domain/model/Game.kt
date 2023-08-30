package com.seftian.capstoneapp.domain.model

import com.google.gson.annotations.SerializedName

data class Game(
    val backgroundImage: String,
    val id: Int,
    val name: String,
    val isFavorite: Boolean
)