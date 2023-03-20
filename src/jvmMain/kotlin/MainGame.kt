import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun MainGame(playMode: PlayMode, gameMode: GameMode, boardSize: Int, recordGame: Boolean) {
    val cellRadius = 15 // TODO: probably parameterize this

    val (boardState, setBoardState) = remember { mutableStateOf(RectangularBoardState<Token>(boardSize, boardSize)) }

    val allowedTokens = listOf(Token('S'), Token('O'))
    val (selTokenIx, setSelTokenIx) = remember { mutableStateOf(0) }

    val onTilePressed by rememberUpdatedState { r: Int, c: Int ->
        setBoardState(boardState.placeTile(allowedTokens[selTokenIx], r, c, 1))
    }

    Column {
        Row {
            // TODO: share these strings
            Text(if (playMode == PlayMode.PassAndPlay) "Pass-and-Play" else "Singleplayer (vs AI)")
            Spacer(Modifier.width(10.dp))
            Text(if (gameMode == GameMode.General) "General" else "Simple")
        }

        Row {
            TokenSelector(allowedTokens, cellRadius, 2, selTokenIx) { setSelTokenIx(it) }
            RectangularBoard(cellRadius, boardState) { r, c -> onTilePressed(r, c) }
        }
    }
}