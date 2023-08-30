package com.seftian.capstoneapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.seftian.capstoneapp.data.local.entity.GamesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GamesDao {

    @Query("SELECT * from games")
    fun getAllGamesFromLocal(): Flow<List<GamesEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertGameToLocal(game: GamesEntity)

    @Update()
    fun updateGameInLocal(game: GamesEntity)
}