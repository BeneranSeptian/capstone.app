package com.seftian.capstoneapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.seftian.capstoneapp.data.local.dao.GamesDao
import com.seftian.capstoneapp.data.local.entity.GamesEntity

@Database(
    entities = [GamesEntity::class],
    version = 1
)
abstract class CapstoneAppDb: RoomDatabase() {
    abstract val gamesDao: GamesDao
}