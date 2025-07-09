package com.dabi.nba_players_list.data.repository

import com.dabi.nba_players_list.data.model.PlayerData
import com.dabi.nba_players_list.data.remote.NbaApiService
import javax.inject.Inject


interface PlayerRepository {
    suspend fun getPlayers(perPage: Int, cursor: Int): List<PlayerData>
}


class PlayerRepositoryImpl @Inject constructor(
    private val nbaApiService: NbaApiService
) : PlayerRepository {
    override suspend fun getPlayers(perPage: Int, cursor: Int): List<PlayerData> {
        return nbaApiService.getPlayers(perPage, cursor).data
    }
}