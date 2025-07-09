package com.dabi.nba_players_list.data.model


/**
 * Represents the response from the API when fetching a list of players.
 *
 * @property data A list of [PlayerData] objects, containing the details of each player.
 */
data class PlayersResponse(
    val data: List<PlayerData>
)