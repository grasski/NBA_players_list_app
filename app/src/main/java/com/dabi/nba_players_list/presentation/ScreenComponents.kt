package com.dabi.nba_players_list.presentation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.dabi.nba_players_list.R
import com.dabi.nba_players_list.data.model.PlayerData
import com.dabi.nba_players_list.utils.NBAColors
import com.dabi.nba_players_list.utils.UiTexts


@OptIn(ExperimentalGlideComposeApi::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.PlayerCard(
    modifier: Modifier = Modifier,
    playerData: PlayerData,
    onClick: (Int) -> Unit,
    animatedContentScope: AnimatedContentScope
) {
    Row(
        modifier = modifier
            .clickable(
                onClick = { onClick(playerData.id) },
                interactionSource = null,
                indication = null
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = playerData.id.toString(),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.Black
            )
        )

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(60.dp)
                .aspectRatio(1f)
                .background(Color.White)
                .border(1.dp, Color.LightGray, CircleShape)
                .background(brush = Brush.horizontalGradient(listOf(NBAColors.blue, NBAColors.red))),
            contentAlignment = Alignment.Center,
        ){
            playerData.imageUrl?.let {  url ->
                GlideImage(
                    model = url,
                    contentDescription = "",
                    modifier = Modifier
                        .clip(CircleShape)
                        .aspectRatio(1f)
                        .background(Color.White)
                        .border(1.dp, Color.LightGray, CircleShape),
                )
            } ?: run{
                val nameChar = playerData.firstName.getOrNull(0) ?: ""
                val lastNameChar = playerData.lastName.getOrNull(0) ?: ""
                val namePlaceholder = nameChar.toString() + lastNameChar.toString()
                BasicText(
                    text = namePlaceholder,
                    style = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold
                    ),
                    maxLines = 1,
                    autoSize = TextAutoSize.StepBased(),
                    modifier = Modifier
                        .padding(10.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    Text(
                        text = playerData.firstName,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .sharedElement(
                                sharedContentState = rememberSharedContentState( "NAME_${playerData.id}"),
                                animatedVisibilityScope = animatedContentScope
                            )
                    )
                    Text(" ",style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    ))
                    Text(
                        text = playerData.lastName,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .sharedElement(
                                sharedContentState = rememberSharedContentState( "LAST_NAME_${playerData.id}"),
                                animatedVisibilityScope = animatedContentScope
                            )
                    )
                }

                GlideImage(
                    model = "https://a.espncdn.com/i/teamlogos/nba/500/${playerData.team.abbreviation}.png",
                    contentDescription = "",
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(40.dp)
                        .aspectRatio(1f)
                        .background(Color.White)
                        .border(1.dp, Color.LightGray, CircleShape)
                        .sharedElement(
                            sharedContentState = rememberSharedContentState("TEAM_IMG_${playerData.id}"),
                            animatedVisibilityScope = animatedContentScope
                        ),
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicText(
                    text = playerData.position,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .sharedElement(
                            sharedContentState = rememberSharedContentState( "POSITION_${playerData.id}"),
                            animatedVisibilityScope = animatedContentScope
                        )
                )
                BasicText(
                    text = playerData.team.fullName,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}


@Composable
fun InfoButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = {
            onClick()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color.Yellow else NBAColors.blue.copy(alpha = 0.5f),
            contentColor = if (isSelected) NBAColors.blue else Color.White
        )
    ) {
        Text(
            text = text
        )
    }
}


@Composable
fun DetailInfoItem(
    title: String,
    value: String
) {
    Row{
        Text(
            text = "$title: ",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.White,
                fontWeight = FontWeight.ExtraBold
            )
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.White
            )
        )
    }
}