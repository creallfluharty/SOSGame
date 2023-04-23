import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import colors.Colors
import screens.*


// TODO: Rename this
@Composable
fun App() {
    val (screenState, setScreenState) = remember { mutableStateOf<ScreenState>(GameSetupState()) }

    MaterialTheme(colors = Colors) {
        when (screenState) {
            is GameSetupState -> GameSetup(screenState, setScreenState) { playMode, gameMode, boardSize, recordGame ->
                setScreenState(GeneralGameState(playMode, gameMode, boardSize, recordGame))
            }
            is GeneralGameState -> GameView(screenState, setScreenState)
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
