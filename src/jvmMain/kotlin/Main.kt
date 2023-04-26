
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import colors.Colors
import models.GeneralGameState
import models.ScreenState
import models.SimpleGameState
import screens.*


// TODO: Rename this
@Composable
fun App() {
    val (screenState, setScreenState) = remember { mutableStateOf<ScreenState>(GameSetupState()) }

    MaterialTheme(colors = Colors) {
        when (screenState) {
            is GameSetupState -> GameSetup(screenState, setScreenState) { playMode, gameMode, boardSize, recordGame ->
                setScreenState(when (gameMode) {
                    GameMode.General -> GeneralGameState(boardSize)
                    GameMode.Simple -> SimpleGameState(boardSize)
                })
            }
//            is GameState<*> -> GameView(screenState, setScreenState)
            is GeneralGameState -> GameView(screenState, setScreenState)
            is SimpleGameState -> GameView(screenState, setScreenState)
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
