package com.dabi.nba_players_list.presentation.playersList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dabi.nba_players_list.R
import com.dabi.nba_players_list.data.model.PlayersListState
import com.dabi.nba_players_list.data.remote.ApiError
import com.dabi.nba_players_list.data.repository.PlayerRepository
import com.dabi.nba_players_list.utils.UiTexts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject


/**
 * Defines the events that can be triggered by the user on the players list screen.
 * These events are handled by the [PlayersListViewModel].
 */
sealed interface PlayersListEvents{
    data object Refresh: PlayersListEvents
}

/**
 * Sealed interface representing side effects that can occur in the PlayersList screen.
 * These events are used for one-time actions defined by [PlayersListViewModel].
 */
sealed interface PlayersListSideEvents{
    data object Idle: PlayersListSideEvents
    data object ShowToast: PlayersListSideEvents
}

/**
 * ViewModel for the Players List screen.
 *
 * This ViewModel is responsible for fetching and managing the list of players,
 * handling user events, and exposing the UI state and side effects to the view.
 *
 * @param playersRepository The repository responsible for fetching player data.
 */
@HiltViewModel
class PlayersListViewModel @Inject constructor(
    private val playersRepository: PlayerRepository,
): ViewModel(){
    private val _state = MutableStateFlow<PlayersListState>(PlayersListState())
    val state = _state.asStateFlow()

    private val _sideEvent = Channel<PlayersListSideEvents>()
    val sideEvent = _sideEvent.receiveAsFlow()

    /**
     * The number of players to fetch per page.
     */
    private val _perPage = 35
    /**
     * The current cursor position for paginating player data.
     * This value is incremented by [_perPage] after each successful fetch.
     */
    private var _cursor = 0

    fun onEvent(event: PlayersListEvents){
        when (event){
            PlayersListEvents.Refresh -> {
                fetchPlayers()
            }
        }
    }

    /**
     * Fetches a list of players from the repository.
     *
     * This function updates the UI state to indicate loading, makes the API call,
     * and then updates the state with the fetched players or an error message.
     * It also handles pagination by updating the `_cursor`.
     * If an error occurs (HttpException or other Exception), it updates the state with an appropriate
     * error message and sends a side event to show a toast.
     *
     * @param count The number of players to fetch. Defaults to `_perPage`.
     */
    private fun fetchPlayers(count: Int = _perPage) {
        _state.update {
            it.copy(error = null)
        }

        if (!_state.value.isLoading){
            viewModelScope.launch {
                _sideEvent.send(PlayersListSideEvents.Idle)

                try {
                    _state.update { it.copy(isLoading = true) }
                    val response = playersRepository.getPlayers(count, _cursor)
                    _state.update { it.copy(isLoading = false) }
                    _cursor += _perPage

                    _state.update {
                        it.copy(players = it.players.plus(response))
                    }
                } catch (e: HttpException) {
                    val apiError = ApiError.entries.find { it.code == e.code() }

                    val error = apiError?.let {
                        UiTexts.StringResource(apiError.getStringId())
                    } ?: run {
                        UiTexts.StringResource(R.string.error_unknown)
                    }

                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = error
                        )
                    }
                    _sideEvent.send(PlayersListSideEvents.ShowToast)
                } catch (e: Exception){
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = UiTexts.StringResource(R.string.error_unknown)
                        )
                    }
                    _sideEvent.send(PlayersListSideEvents.ShowToast)
                }
            }
        }
    }

}