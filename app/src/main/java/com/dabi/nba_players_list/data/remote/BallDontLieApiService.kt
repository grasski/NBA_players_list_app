package com.dabi.nba_players_list.data.remote

import com.dabi.nba_players_list.BuildConfig
import com.dabi.nba_players_list.data.model.BallDontLieResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface BallDontLieApiService {
    @GET("players")
    @Headers("Authorization: ${BuildConfig.API_KEY}")
    suspend fun getPlayers(
        @Query("per_page") perPage: Int,
        @Query("cursor") cursor: Int
    ): BallDontLieResponse
}
