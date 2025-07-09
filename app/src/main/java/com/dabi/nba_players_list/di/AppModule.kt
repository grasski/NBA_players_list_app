package com.dabi.nba_players_list.di

import com.dabi.nba_players_list.data.remote.NbaApiService
import com.dabi.nba_players_list.data.repository.PlayerRepository
import com.dabi.nba_players_list.data.repository.PlayerRepositoryImpl
import com.dabi.nba_players_list.presentation.playersList.PlayersListViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule{

    @Provides
    @Singleton
    fun provideApiInstance(): NbaApiService {
        try {
            return Retrofit.Builder()
                .baseUrl("https://api.balldontlie.io/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NbaApiService::class.java)
        } catch (e: Exception){
            throw e
        }
    }

    @Provides
    @Singleton
    fun providePlayerRepository(nbaApiService: NbaApiService): PlayerRepository {
        return PlayerRepositoryImpl(nbaApiService)
    }

    @Provides
    @Singleton
    fun providePlayersListViewModel(playerRepository: PlayerRepository): PlayersListViewModel {
        return PlayersListViewModel(playerRepository)
    }
}