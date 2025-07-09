package com.dabi.nba_players_list.di

import com.dabi.nba_players_list.data.remote.BallDontLieApiService
import com.dabi.nba_players_list.data.remote.SportsDbApiService
import com.dabi.nba_players_list.data.repository.PlayersRepository
import com.dabi.nba_players_list.data.repository.PlayersRepositoryImpl
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
    fun provideBallDontLieApiInstance(): BallDontLieApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.balldontlie.io/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BallDontLieApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideSportsDbApiInstance(): SportsDbApiService {
        return Retrofit.Builder()
            .baseUrl("https://www.thesportsdb.com/api/v1/json/123/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SportsDbApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePlayerRepository(ballDontLieApiService: BallDontLieApiService, sportsDbApiService: SportsDbApiService): PlayersRepository {
        return PlayersRepositoryImpl(ballDontLieApiService, sportsDbApiService)
    }

    @Provides
    @Singleton
    fun providePlayersListViewModel(playersRepository: PlayersRepository): PlayersListViewModel {
        return PlayersListViewModel(playersRepository)
    }
}