package com.seftian.capstoneapp.domain.repository

import com.seftian.capstoneapp.data.ResourceState
import com.seftian.capstoneapp.data.local.entity.GamesEntity
import com.seftian.capstoneapp.domain.model.Game
import com.seftian.capstoneapp.domain.model.GameDetail
import kotlinx.coroutines.flow.Flow

interface IGamesRepository {
    fun getGames(): Flow<List<Game>>
    suspend fun insertGames(games: List<Game>)
    suspend fun updateGame(game: Game)
    suspend fun refreshGames()
    fun getGameDetail(id: Int): Flow<ResourceState<GameDetail>>
}