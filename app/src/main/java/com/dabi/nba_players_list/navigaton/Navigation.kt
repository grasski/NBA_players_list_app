package com.dabi.nba_players_list.navigaton

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.dabi.nba_players_list.presentation.playerDetail.PlayerDetailScreen
import com.dabi.nba_players_list.presentation.playersList.PlayersListScreen
import com.dabi.nba_players_list.presentation.playersList.PlayersListSideEvents
import com.dabi.nba_players_list.presentation.playersList.PlayersListViewModel


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun Navigation() {
    val navController = rememberNavController()

    val viewModel = hiltViewModel<PlayersListViewModel>()
    val onEvent = viewModel::onEvent
    val state by viewModel.state.collectAsStateWithLifecycle()

    SharedTransitionLayout {
        NavHost(
            navController = navController,
            startDestination = Screens.Home.toString()
        ) {
            composable(
                Screens.Home.toString(),
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { -it }
                    )
                },
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { -it }
                    )
                }
            ) {
                val sideEvent by viewModel.sideEvent.collectAsStateWithLifecycle(PlayersListSideEvents.Idle)
                PlayersListScreen(
                    navController = navController,
                    state = state,
                    sideEvents = sideEvent,
                    onEvent = onEvent,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this
                )
            }

            composable<Screens.DetailPlayer>(
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { it }
                    )
                },
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { it }
                    )
                }
            ) {
                val id = it.toRoute<Screens.DetailPlayer>().id
                val playerData = state.players.first { p-> p.id == id }

                PlayerDetailScreen(
                    navController = navController,
                    playerData = playerData,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this
                )
            }
        }
    }
}