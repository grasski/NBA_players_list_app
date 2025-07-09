package com.dabi.nba_players_list.data.model

import com.google.gson.annotations.SerializedName


/**
 * Represents the response from the API when fetching a list of players.
 *
 * @property data A list of [PlayerData] objects, containing the details of each player.
 */
data class BallDontLieResponse(
    val data: List<PlayerData>
)


/**
 * Represents the response from the SportsDB API when fetching player details.
 *
 * @property players A list of [Players] objects, potentially null if no players are found or if there's an error.
 */
data class SportsDbResponse(
    @SerializedName("player")
    val players: List<Players>?
)
data class Players(
    val strPlayer: String?,
    val strCutout: String?
)