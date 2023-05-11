
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import colors.Colors
import components.GameReplay
import components.GameReplayState
import models.*
import screens.*


// TODO: Rename this
@Composable
fun App() {
    val (screenState, setScreenState) = remember { mutableStateOf<ScreenState>(GameSetupState()) }

    MaterialTheme(colors = Colors) {
        when (screenState) {
            is GameSetupState -> GameSetup(screenState, setScreenState) { player1Type, player2Type, gameMode, boardSize -> // TODO: GameConfiguration
                // TODO: List<PlayerType>
                val player1 = when (player1Type) {
                    PlayerType.Human -> HumanPlayer(0, Color.Red)
                    PlayerType.Computer -> ComputerPlayer(0, Color.Red)
                }
                val player2 = when (player2Type) {
                    PlayerType.Human -> HumanPlayer(1, Color.Green)
                    PlayerType.Computer -> ComputerPlayer(1, Color.Green)
                }
                val players = listOf(player1, player2)
                setScreenState(GameViewState(when (gameMode) {
                    GameMode.General -> GeneralGameState(boardSize, players)
                    GameMode.Simple -> SimpleGameState(boardSize, players)
                }))
            }
            is GameViewState -> GameView(screenState, setScreenState) { width, height, players, history ->
                setScreenState(GameReplayState(width, height, players, history))
            }
            is GameReplayState -> GameReplay(screenState, setScreenState)
        }
    }
}

fun main() = application {
    val windowState = rememberWindowState(size = DpSize.Unspecified)

    Window(
        onCloseRequest = ::exitApplication,
        state = windowState,
        icon = painterResource("RescueBuoy.png"),
        title = "SOS!",
    ) {
        App()
    }
}
