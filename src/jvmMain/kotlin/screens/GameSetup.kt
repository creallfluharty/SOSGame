package screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import components.*


enum class PlayMode {
    Singleplayer,
    PassAndPlay,
}

enum class GameMode {
    Simple,
    General,
}


@Composable
@Preview
fun GameSetup(state: GameSetupState, setState: (GameSetupState) -> Unit, onGameStart: (PlayMode, GameMode, Int, Boolean) -> Unit) {
    ProvideTextStyle(TextStyle(color = Color.White)) {
        Column(Modifier
                .width(800.dp)
                .height(500.dp)
                .background(Color.Blue),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource("SOS.png"),
                contentDescription = "game logo",
                modifier = Modifier.height(100.dp)
            )
            Spacer(Modifier.height(10.dp))
            Column(Modifier
                    .width(300.dp)
                    .border(
                        BorderStroke(1.dp, SolidColor(MaterialTheme.colors.primary)),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(10.dp)
            ) {
                RadioSelection(state.playModeSelection, onOptionSelect = { setState(state.selectPlayMode(it)) })

                Divider(Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(MaterialTheme.colors.primary)
                )

                RadioSelection(state.gameModeSelection, onOptionSelect = { setState(state.selectGameMode(it)) })

                // TODO: stop copying this
                Divider(Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(MaterialTheme.colors.primary)
                )
                // TODO: Extract this into a new component
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = state.recordGame, onCheckedChange = { setState(state.setRecordGame(it)) })
                    Text("Record Game?")
                }
                Divider(Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(MaterialTheme.colors.primary)
                )
                NumberInputField(
                    "Board Size",
                    state.boardSizeInput,
                ) { setState(state.replaceBoardSizeInput(it)) }
            }
            Button(
                onClick = {
                    onGameStart(state.getSelectedPlayMode(), state.getSelectedGameMode(), state.getBoardSize(), state.recordGame)
                },
                enabled = state.checkBoardSizeIsValid(),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
            ) { Text("New Game") }
        }
    }
}