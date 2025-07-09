package com.dabi.nba_players_list.presentation.playersList

import android.widget.Toast
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.dabi.nba_players_list.R
import com.dabi.nba_players_list.data.model.PlayersListState
import com.dabi.nba_players_list.navigaton.Screens
import com.dabi.nba_players_list.presentation.PlayerCard
import com.dabi.nba_players_list.utils.NBAColors
import com.dabi.nba_players_list.utils.UiTexts


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun PlayersListScreen(
    navController: NavController,
    state: PlayersListState,
    sideEvents: PlayersListSideEvents,
    onEvent: (PlayersListEvents) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    val context = LocalContext.current
    LaunchedEffect(sideEvents) {
        when(sideEvents){
            PlayersListSideEvents.Idle -> {}
            PlayersListSideEvents.ShowToast -> {
                state.error?.let { error ->
                    Toast.makeText(context, error.asString(context), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    val columnState = rememberLazyListState()
    LazyColumn(
        state = columnState,
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        NBAColors.blue.copy(alpha = 0.5f), Color.White.copy(alpha = 0.5f), NBAColors.red.copy(alpha = 0.5f)
                    )
                )
            )
            .paint(
                painter = painterResource(R.drawable.nba_logo),
                alpha = 0.1f,
                contentScale = ContentScale.Crop
            )
            .padding(horizontal = 8.dp),
        contentPadding = PaddingValues(
            bottom = WindowInsets.safeDrawing.asPaddingValues().calculateBottomPadding(),
            top = WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding(),
            start = WindowInsets.safeDrawing.asPaddingValues().calculateStartPadding(LayoutDirection.Ltr),
            end = WindowInsets.safeDrawing.asPaddingValues().calculateEndPadding(LayoutDirection.Ltr)
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.Top),
    ) {
        itemsIndexed(state.players){ index, player ->
            with(sharedTransitionScope) {
                PlayerCard(
                    playerData = player,
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = { id ->
                        // Multiple clicks will be consumed
                        if (navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
                            navController.navigate(Screens.DetailPlayer(id))
                        }
                    },
                    animatedContentScope = animatedContentScope
                )
            }

            HorizontalDivider(modifier = Modifier.padding(top = 12.dp))
        }

        item {
            LaunchedEffect(Unit) {
                // This makes also the first call for players fetching.
                onEvent(PlayersListEvents.Refresh)

                columnState.scrollToItem(state.players.size + 1)
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator()
                } else if (state.error != null){
                    Button(
                        onClick = { onEvent(PlayersListEvents.Refresh) },
                        modifier = Modifier
                            .padding(bottom = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NBAColors.blue,
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = UiTexts.StringResource(R.string.btn_refresh).asString()
                        )
                    }
                }
            }
        }
    }
}
