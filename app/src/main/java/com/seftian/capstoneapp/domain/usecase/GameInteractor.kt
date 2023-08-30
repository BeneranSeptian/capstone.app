package com.seftian.capstoneapp.domain.usecase

import com.seftian.capstoneapp.data.ResourceState
import com.seftian.capstoneapp.domain.model.Game
import com.seftian.capstoneapp.domain.model.GameDetail
import com.seftian.capstoneapp.domain.repository.IGamesRepository
import kotlinx.coroutines.flow.Flow

class GameInteractor(private val gamesRespository: IGamesRepository): GamesUseCase {
    override fun getAllGames(): Flow<List<Game>> {
        return gamesRespository.getGames()
    }

    override suspend fun refreshGames() {
        gamesRespository.refreshGames()
    }

    override suspend fun updateGame(game: Game) {
        gamesRespository.updateGame(game)
    }

    override fun getDetailGame(id: Int): Flow<ResourceState<GameDetail>> {
        return gamesRespository.getGameDetail(id)
    }
}