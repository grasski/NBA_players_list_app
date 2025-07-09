package com.dabi.nba_players_list.presentation.playerDetail

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.dabi.nba_players_list.R
import com.dabi.nba_players_list.data.model.PlayerData
import com.dabi.nba_players_list.presentation.DetailInfoItem
import com.dabi.nba_players_list.presentation.InfoButton
import com.dabi.nba_players_list.utils.NBAColors
import com.dabi.nba_players_list.utils.UiTexts
import com.dabi.nba_players_list.utils.footInchToCm
import com.dabi.nba_players_list.utils.poundToKg


@OptIn(ExperimentalGlideComposeApi::class, ExperimentalSharedTransitionApi::class)
@Composable
fun PlayerDetailScreen(
    navController: NavController,
    playerData: PlayerData,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(NBAColors.blue)
        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .padding(
                        PaddingValues(
                            top = WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding(),
                            start = WindowInsets.safeDrawing.asPaddingValues().calculateStartPadding(LayoutDirection.Ltr),
                            end = WindowInsets.safeDrawing.asPaddingValues().calculateEndPadding(LayoutDirection.Ltr)
                        )
                    )
            ){
                with(sharedTransitionScope){
                    GlideImage(
                        model = "https://a.espncdn.com/i/teamlogos/nba/500/${playerData.team.abbreviation}.png",
                        contentDescription = "",
                        modifier = Modifier
                            .padding(26.dp)
                            .clip(CircleShape)
                            .fillMaxSize()
                            .aspectRatio(1f)
                            .alpha(0.5f)
                            .blur(2.dp)
                            .sharedElement(
                                sharedContentState = rememberSharedContentState("TEAM_IMG_${playerData.id}"),
                                animatedVisibilityScope = animatedContentScope,
                                renderInOverlayDuringTransition = false
                            ),
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight(0.65f)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    IconButton(
                        onClick = {
                            if (navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
                                navController.navigateUp()
                            }
                        }
                    ) {
                        Icon(
                            Icons.AutoMirrored.Outlined.ArrowBack,
                            null,
                            tint = Color.White,
                            modifier = Modifier
                                .sizeIn(minWidth = 40.dp, minHeight = 40.dp)
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        with(sharedTransitionScope){
                            BasicText(
                                text = playerData.firstName,
                                autoSize = TextAutoSize.StepBased(),
                                style = MaterialTheme.typography.labelLarge.copy(
                                    color = Color.White
                                ),
                                maxLines = 1,
                                modifier = Modifier.weight(0.5f)
                                    .sharedElement(
                                        sharedContentState = rememberSharedContentState( "NAME_${playerData.id}"),
                                        animatedVisibilityScope = animatedContentScope
                                    ),
                            )
                            BasicText(
                                text = playerData.lastName,
                                autoSize = TextAutoSize.StepBased(),
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                ),
                                maxLines = 1,
                                modifier = Modifier.weight(1f)
                                    .sharedElement(
                                        sharedContentState = rememberSharedContentState( "LAST_NAME_${playerData.id}"),
                                        animatedVisibilityScope = animatedContentScope
                                    )
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start)
                ){
                    Text(
                        text = "#${playerData.jerseyNumber}",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            fontSize = 50.sp
                        ),
                        maxLines = 1,
                    )

                    VerticalDivider(
                        modifier = Modifier.height(50.sp.value.dp),
                        color = Color.White,
                        thickness = 2.dp
                    )

                    with(sharedTransitionScope){
                        Text(
                            text = playerData.position,
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                fontSize = 50.sp
                            ),
                            modifier = Modifier
                                .sharedBounds(
                                    sharedContentState = rememberSharedContentState( "POSITION_${playerData.id}"),
                                    animatedVisibilityScope = animatedContentScope,
                                )
                        )
                    }
                }

                var visible by rememberSaveable { mutableStateOf(false) }
                LaunchedEffect(Unit) {
                    visible = true
                }
                AnimatedVisibility(
                    visible = visible,
                    enter = slideInVertically{ it },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .fillMaxHeight(1f)
                        .fillMaxWidth(0.5f)
                ){
                    playerData.imageUrl?.let { url ->
                        Box(
                            contentAlignment = Alignment.BottomCenter
                        ){
                            GlideImage(
                                model = url,
                                contentDescription = "",
                            )
                        }
                    } ?: run {
                        Image(
                            painter = painterResource(R.drawable.player_silhouette),
                            contentDescription = null,
                        )
                    }
                }
            }
        }

        Column (
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .clip(RoundedCornerShape(topEnd = 40.dp, topStart = 40.dp))
                .background(NBAColors.red)
        ){
            var selectedInfo by remember { mutableIntStateOf(0) }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                InfoButton(
                    text = UiTexts.StringResource(R.string.btn_player_info).asString(),
                    isSelected = selectedInfo == 0,
                ) { selectedInfo = 0 }

                InfoButton(
                    text = UiTexts.StringResource(R.string.btn_team_info).asString(),
                    isSelected = selectedInfo == 1,
                ) { selectedInfo = 1 }
            }

            AnimatedContent(
                targetState = selectedInfo,
                modifier = Modifier.fillMaxSize(),
                transitionSpec = {
                    slideInHorizontally(
                        initialOffsetX = { -it }
                    ).togetherWith(
            slideOutHorizontally(
                        targetOffsetX = { -it }
                    ))
                }
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(
                        bottom = WindowInsets.safeDrawing.asPaddingValues().calculateBottomPadding(),
                        start = WindowInsets.safeDrawing.asPaddingValues().calculateStartPadding(LayoutDirection.Ltr),
                        end = WindowInsets.safeDrawing.asPaddingValues().calculateEndPadding(LayoutDirection.Ltr)
                    )
                ) {
                    if (it == 0){
                        item {
                            DetailInfoItem(
                                title = UiTexts.StringResource(R.string.player_info_height).asString(),
                                value = playerData.height.footInchToCm().toString() + " cm"
                            )
                        }
                        item {
                            DetailInfoItem(
                                title = UiTexts.StringResource(R.string.player_info_weight).asString(),
                                value = playerData.weight.poundToKg().toString() + " kg"
                            )
                        }
                        item {
                            DetailInfoItem(
                                title = UiTexts.StringResource(R.string.player_info_college).asString(),
                                value = playerData.college
                            )
                        }
                        item {
                            DetailInfoItem(
                                title = UiTexts.StringResource(R.string.player_info_country).asString(),
                                value = playerData.country
                            )
                        }
                        item {
                            DetailInfoItem(
                                title = UiTexts.StringResource(R.string.player_info_draft_year).asString(),
                                value = playerData.draftYear.toString()
                            )
                        }
                        item {
                            DetailInfoItem(
                                title = UiTexts.StringResource(R.string.player_info_draft_number).asString(),
                                value = playerData.draftNumber.toString()
                            )
                        }
                        item {
                            DetailInfoItem(
                                title = UiTexts.StringResource(R.string.player_info_draft_round).asString(),
                                value = playerData.draftRound.toString()
                            )
                        }
                    } else {
                        item {
                            DetailInfoItem(
                                title = UiTexts.StringResource(R.string.team_info_full_name)
                                    .asString(),
                                value = playerData.team.fullName
                            )
                        }
                        item {
                            DetailInfoItem(
                                title = UiTexts.StringResource(R.string.team_info_abbreviation)
                                    .asString(),
                                value = playerData.team.abbreviation
                            )
                        }
                        item {
                            DetailInfoItem(
                                title = UiTexts.StringResource(R.string.team_info_city)
                                    .asString(),
                                value = playerData.team.city
                            )
                        }
                        item {
                            DetailInfoItem(
                                title = UiTexts.StringResource(R.string.team_info_division)
                                    .asString(),
                                value = playerData.team.division
                            )
                        }
                        item {
                            DetailInfoItem(
                                title = UiTexts.StringResource(R.string.team_info_conference)
                                    .asString(),
                                value = playerData.team.conference
                            )
                        }
                    }
                }
            }
        }
    }
}
