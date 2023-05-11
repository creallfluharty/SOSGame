package screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import components.NumberInputField
import components.RadioSelection
import components.ToggleCarousel


enum class GameMode {
    Simple,
    General,
}

enum class PlayerType {
    Human,
    Computer,
}


@Composable
@Preview
fun GameSetup(state: GameSetupState, setState: (GameSetupState) -> Unit, onGameStart: (PlayerType, PlayerType, GameMode, Int) -> Unit) {
    @Composable
    fun divider() {
        Divider(Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(MaterialTheme.colors.primary)
        )
    }

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
                ToggleCarousel(state.player1TypeToggle, "player 1 type", 50.dp) { setState(state.togglePlayer1Type()) }
                ToggleCarousel(state.player2TypeToggle, "player 2 type", 50.dp) { setState(state.togglePlayer2Type()) }

                divider()

                RadioSelection(state.gameModeSelection, onOptionSelect = { setState(state.selectGameMode(it)) })
                divider()

                NumberInputField(
                    "Board Size",
                    state.boardSizeInput,
                ) { setState(state.replaceBoardSizeInput(it)) }
            }
            Button(
                onClick = {
                    onGameStart(
                        state.getPlayer1Type(),
                        state.getPlayer2Type(),
                        state.getSelectedGameMode(),
                        state.getBoardSize(),
                    )
                },
                enabled = state.checkBoardSizeIsValid(),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
            ) { Text("New Game") }
        }
    }
}