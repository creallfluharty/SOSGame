package screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import components.PlayerInfoView
import components.RectangularBoard
import components.TokenSelector
import models.GameState
import models.GeneralGameState


@Composable
fun <G: GameState<G>> GameView(state: G, setState: (G) -> Unit) {
    val cellRadius = 15 // TODO: probably parameterize this

    Column {
        Row {
            // TODO: share these strings
            Text(if (state is GeneralGameState) "General" else "Simple")
        }

        PlayerInfoView(state.getStatus(), state.getPlayers(), (2 * cellRadius).dp)

        Row {
            TokenSelector(cellRadius, 1, state.getTokenSelectorState()) {
                setState(state.setTokenSelectionIndex(it))
            }
            RectangularBoard(cellRadius, state.getBoardState(), state.getPlayers()) { r, c ->
                if (!state.checkGameEnded())
                    setState(state.placeToken(state.getSelectedToken(), r, c, state.getTurn()))
            }
        }
    }
}