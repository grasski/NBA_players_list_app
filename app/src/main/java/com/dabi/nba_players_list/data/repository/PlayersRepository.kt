package com.dabi.nba_players_list.data.repository

import com.dabi.nba_players_list.data.model.PlayerData
import com.dabi.nba_players_list.data.remote.BallDontLieApiService
import com.dabi.nba_players_list.data.remote.SportsDbApiService
import javax.inject.Inject


interface PlayersRepository {
    suspend fun getPlayers(perPage: Int, cursor: Int): List<PlayerData>
    suspend fun getPlayerImageUrl(playerName: String): String?
}


class PlayersRepositoryImpl @Inject constructor(
    private val ballDontLieApiService: BallDontLieApiService,
    private val sportsDbApiService: SportsDbApiService
) : PlayersRepository {
    override suspend fun getPlayers(perPage: Int, cursor: Int): List<PlayerData> {
        return ballDontLieApiService.getPlayers(perPage, cursor).data
    }

    override suspend fun getPlayerImageUrl(playerName: String): String? {
        return sportsDbApiService.getPlayerImageUrl(playerName).players?.find { it.strPlayer == playerName }?.strCutout
    }
}