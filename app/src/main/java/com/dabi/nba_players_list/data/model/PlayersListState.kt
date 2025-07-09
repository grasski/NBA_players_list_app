package com.dabi.nba_players_list.data.model

import com.dabi.nba_players_list.utils.UiTexts


data class PlayersListState(
    val players: List<PlayerData> = emptyList(),
    val isLoading: Boolean = false,
    val error: UiTexts? = null
)
