package com.seftian.capstoneapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "games"
)
data class GamesEntity(
    @PrimaryKey
    val id: Int,
    val backgroundImage: String,
    val name: String,
    val isFavorite: Boolean = false,
//    val description: String? = null,
//    val released: String? = null,
//    val screenshotsCount: Int? = null,
)