package com.seftian.capstoneapp

import com.seftian.capstoneapp.data.local.entity.GamesEntity
import com.seftian.capstoneapp.data.remote.response.GameDetailResponse
import com.seftian.capstoneapp.data.remote.response.GameItemResponse
import com.seftian.capstoneapp.data.remote.response.GamesResponse
import com.seftian.capstoneapp.domain.model.Game

object DataHelper {
    fun mapGameEntitiesToGameDomain(input: List<GamesEntity>): List<Game> {
        return input.map { gameEntity ->
            Game(
                id = gameEntity.id,
                backgroundImage = gameEntity.backgroundImage,
                name = gameEntity.name,
                isFavorite = gameEntity.isFavorite

            )
        }
    }

    fun mapGamesResponseToGameEntity(input: List<GameItemResponse>): List<GamesEntity> {
        return input.map { gameResponse ->
            GamesEntity(
                id = gameResponse.id,
                name = gameResponse.name,
                backgroundImage = gameResponse.backgroundImage,
            )
        }
    }

    fun mapGameToGameEntity(input: Game): GamesEntity {
        return GamesEntity(
            id = input.id,
            name = input.name,
            backgroundImage = input.backgroundImage,
            isFavorite = input.isFavorite
        )
    }

//    fun mapGameDetailResponseToGameEntity(input: GameDetailResponse): GamesEntity {
//        return GamesEntity(
//            id = input.id,
//            name = input.name,
//            backgroundImage =
//        )
//    }
}