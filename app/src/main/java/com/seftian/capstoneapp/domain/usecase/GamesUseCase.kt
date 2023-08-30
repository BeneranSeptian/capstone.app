package com.seftian.capstoneapp.domain.usecase

import com.seftian.capstoneapp.data.ResourceState
import com.seftian.capstoneapp.domain.model.Game
import com.seftian.capstoneapp.domain.model.GameDetail
import kotlinx.coroutines.flow.Flow

interface GamesUseCase {
    fun getAllGames(): Flow<List<Game>>
    suspend fun refreshGames()
    suspend fun updateGame(game: Game)
    fun getDetailGame(id: Int): Flow<ResourceState<GameDetail>>
}