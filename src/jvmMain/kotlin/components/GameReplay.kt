package components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString

@Composable
fun GameReplay(state: GameReplayState, setState: (GameReplayState) -> Unit) {
    val cellRadius = 15 // TODO: probably parameterize this

    Column {
        RectangularBoard(cellRadius, state.boardState, state.players) { _, _ -> }
        Row {
            Button(onClick = { setState(state.advance()!!) }, enabled = state.checkCanAdvance()) {
                Text("Next")
            }

            val clipboardManager = LocalClipboardManager.current

            Button(onClick = {
                clipboardManager.setText(AnnotatedString(state.getHistoryPrintout()))
            }) {
                Text("Copy to Clipboard")
            }
        }
    }
}