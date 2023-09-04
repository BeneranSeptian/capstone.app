package com.seftian.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.seftian.core.data.local.dao.MoviesDao
import com.seftian.core.data.local.entity.MoviesEntity

@Database(
    entities = [MoviesEntity::class],
    version = 1
)
@TypeConverters(GenreConverter::class)
abstract class CapstoneAppDb: RoomDatabase() {
    abstract val moviesDao: MoviesDao
}