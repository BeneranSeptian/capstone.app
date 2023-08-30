package com.seftian.capstoneapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class GameItemResponse(
    @field:SerializedName("background_image")
    val backgroundImage: String,
    val id: Int,
    val name: String,
)