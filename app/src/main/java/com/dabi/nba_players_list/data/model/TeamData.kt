package com.dabi.nba_players_list.data.model

import com.google.gson.annotations.SerializedName


data class TeamData(
    val id: Int,
    val conference: String,
    val division: String,
    val city: String,
    val name: String,
    @SerializedName("full_name")
    val fullName: String,
    val abbreviation: String
)
