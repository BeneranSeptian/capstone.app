package com.seftian.capstoneapp.data.remote.response

data class GamesResponse(
    val count: Int,
    val next: String,
    val results: List<GameItemResponse>,
)