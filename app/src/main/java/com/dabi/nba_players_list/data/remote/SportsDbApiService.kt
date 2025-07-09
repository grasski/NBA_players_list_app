package com.dabi.nba_players_list.data.remote

import com.dabi.nba_players_list.data.model.SportsDbResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface SportsDbApiService {
    @GET("searchplayers.php")
    suspend fun getPlayerImageUrl(
        @Query("p") playerName: String
    ): SportsDbResponse
}