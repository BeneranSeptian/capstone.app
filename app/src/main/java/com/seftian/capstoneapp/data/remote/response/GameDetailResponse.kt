package com.seftian.capstoneapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class GameDetailResponse(
    val id: Int,
    val name: String,
    val description: String,
    val released: String,
    @field:SerializedName("screenshots_count")
    val screenshotsCount: Int,
)