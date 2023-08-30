package com.seftian.capstoneapp.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.seftian.capstoneapp.data.remote.response.Genre

object GenreConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromGenreList(genres: List<Genre>?): String? {
        return genres?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toGenreList(genreData: String?): List<Genre>? {
        return genreData?.let {
            val listType = object : TypeToken<List<Genre>>() {}.type
            gson.fromJson<List<Genre>>(it, listType)
        }
    }
}





