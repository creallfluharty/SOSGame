package screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import components.AlreadyOccupiedException
import components.PlayerInfoView
import components.RectangularBoard
import models.GameStatus
import models.PlayAction
import models.Player
import models.PlayerID


@Composable
fun GameView(
    state: GameViewState,
    setState: (GameViewState) -> Unit,
    onReplayRequested: (width: Int, height: Int, List<Player>, List<Pair<PlayAction, PlayerID>>) -> Unit,
) {
    val cellRadius = 15 // TODO: probably parameterize this

    val gameState = state.gameState
    val status = gameState.getStatus()

    Column {
        Row {
            Text(gameState.getModeString())
        }

        PlayerInfoView(status, gameState.getPlayers(), (2 * cellRadius).dp) // TODO: just pass turn instead of status

        when (status) {
            is GameStatus.Tie, is GameStatus.Won -> {
                RectangularBoard(cellRadius, gameState.getBoardState(), gameState.getPlayers()) { _, _ -> }
                Button(onClick = {
                    // TODO: pass in a blank board or a fresh game state, not width/height and players (add a GameState.reset() method)
                    onReplayRequested(gameState.getBoardState().width, gameState.getBoardState().height, gameState.getPlayers(), gameState.getHistory())
                }) {
                    Text("Click For Replay")
                }
            }
            is GameStatus.Ongoing -> {
                val player = gameState.getPlayers()[gameState.getTurn()]
                player.playerInterface(
                    gameState,
                    cellRadius,
                    { setState(state.updateGameState(gameState.updatePlayer(player.getID(), it))) },
                ) {
                    try {
                        when (it) {
                            is PlayAction.PlaceTile -> setState(state.updateGameState(gameState.placeToken(it, player.getID())))
                        }
                    } catch (_: AlreadyOccupiedException) {}
                }
            }
        }
    }
}