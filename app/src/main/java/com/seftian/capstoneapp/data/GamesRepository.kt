package com.seftian.capstoneapp.data

import androidx.room.Query
import com.seftian.capstoneapp.DataHelper
import com.seftian.capstoneapp.data.local.LocalDataSource
import com.seftian.capstoneapp.data.local.entity.GamesEntity
import com.seftian.capstoneapp.data.remote.RemoteDataSource
import com.seftian.capstoneapp.data.remote.network.ResponseStatus
import com.seftian.capstoneapp.domain.model.Game
import com.seftian.capstoneapp.domain.model.GameDetail
import com.seftian.capstoneapp.domain.repository.IGamesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GamesRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : IGamesRepository {

    override fun getGames(): Flow<List<Game>> {
        val gamesFromLocal = localDataSource.getAllGames()
        return gamesFromLocal.map { DataHelper.mapGameEntitiesToGameDomain(it) }
    }

    override suspend fun insertGames(games: List<Game>) {
        if (games.isEmpty()) {
            return
        }

        withContext(Dispatchers.IO){
            games.map {
                val gameEntity = DataHelper.mapGameToGameEntity(it)
                localDataSource.insertGame(game = gameEntity)
            }
        }
    }

    override suspend fun updateGame(game: Game) {
        val gameEntity = DataHelper.mapGameToGameEntity(game)
        withContext(Dispatchers.IO){
            localDataSource.updateGame(gameEntity)
        }
    }

    override suspend fun refreshGames() {
        when (val response = remoteDataSource.getGames().first()) {
            is ResponseStatus.Success -> {
                withContext(Dispatchers.IO){
                    val gameEntities = DataHelper.mapGamesResponseToGameEntity(response.data)
                    gameEntities.map {
                        localDataSource.insertGame(it)
                    }
                }
            }
            is ResponseStatus.Error -> {
                println("Error fetching games: ${response.message}")
            }
        }
    }

    override fun getGameDetail(id: Int): Flow<ResourceState<GameDetail>> {
        return flow{
            emit(ResourceState.Loading)

            when (val response = remoteDataSource.getGameDetail(id).first()) {
                is ResponseStatus.Success -> {
                    val data = response.data
                    val gameDetail: GameDetail = GameDetail(
                        id = data.id,
                        name= data.name,
                        screenshotsCount = data.screenshotsCount,
                        description = data.description,
                        released = data.released
                    )
                    // 3. Emit Success state with the data
                    emit(ResourceState.Success(gameDetail))
                }
                is ResponseStatus.Error -> {
                    // 4. Emit Error state with the error message
                    emit(ResourceState.Error(response.message))
                }
            }
        }
    }

}