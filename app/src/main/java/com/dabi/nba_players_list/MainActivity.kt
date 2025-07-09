package com.dabi.nba_players_list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.dabi.nba_players_list.navigaton.Navigation
import com.dabi.nba_players_list.ui.theme.NBA_players_listTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NBA_players_listTheme {
                Navigation()
            }
        }
    }
}
