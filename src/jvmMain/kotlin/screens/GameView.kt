package screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import components.RectangularBoard
import components.TokenSelector


@Composable
fun GameView(state: GeneralGameState, setState: (GeneralGameState) -> Unit) {
    val cellRadius = 15 // TODO: probably parameterize this

    Column {
        Row {
            // TODO: share these strings
            Text(if (state.playMode == PlayMode.PassAndPlay) "Pass-and-Play" else "Singleplayer (vs AI)")
            Spacer(Modifier.width(10.dp))
            Text(if (state.gameMode == GameMode.General) "General" else "Simple")
        }

        Row {
            TokenSelector(cellRadius, 1, state.tokenSelectorState) {
                setState(state.setTokenSelectionIndex(it))
            }
            RectangularBoard(cellRadius, state.boardState, state.players) { r, c ->
                setState(state.placeToken(state.getSelectedToken(), r, c, state.turn))
            }
        }
    }
}