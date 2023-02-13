import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import colors.DarkColors


sealed class Screen {
    object NewGame : Screen()
    data class MainGame(val gameMode: GameMode, val boardSize: Int, val recordGame: Boolean): Screen()
}

@Composable
@Preview
fun App() {
    var screenState by remember { mutableStateOf<Screen>(Screen.NewGame) }

    MaterialTheme(colors = DarkColors) {
        when (val screen = screenState) {
            is Screen.NewGame -> GameSetup { gameMode, boardSize, recordGame ->
                screenState = Screen.MainGame(gameMode, boardSize, recordGame)
            }
            is Screen.MainGame -> MainGame(screen.gameMode, screen.boardSize, screen.recordGame)
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
