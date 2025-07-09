package com.dabi.nba_players_list.navigaton

import kotlinx.serialization.Serializable


sealed interface Screens{
    @Serializable data object Home: Screens
    @Serializable data class DetailPlayer(val id: Int): Screens
}