package com.seftian.capstoneapp.data.local

import com.seftian.capstoneapp.data.local.dao.GamesDao
import com.seftian.capstoneapp.data.local.entity.GamesEntity
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val gamesDao: GamesDao) {

    fun getAllGames(): Flow<List<GamesEntity>> = gamesDao.getAllGamesFromLocal()

    fun insertGame(game: GamesEntity){
        gamesDao.insertGameToLocal(game)
    }

    fun updateGame(game: GamesEntity){
        gamesDao.updateGameInLocal(game)
    }
}