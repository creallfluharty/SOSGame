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
fun GameSetup(onGameStart: (PlayMode, GameMode, Int, Boolean) -> Unit) {
    val playModes = hashMapOf("Singleplayer (vs AI)" to PlayMode.Singleplayer, "Pass-and-Play" to PlayMode.PassAndPlay)
    val gameModes = hashMapOf("Simple" to GameMode.Simple, "General" to GameMode.General)
    var selectedPlayMode by remember { mutableStateOf("Pass-and-Play") }
    var selectedGameMode by remember { mutableStateOf("General") }
    var boardSizeInput by remember { mutableStateOf("8") }
    var recordGame by remember { mutableStateOf(false) }

    val boardSizeIsValid = try {
        val boardSize = boardSizeInput.toInt()
        boardSize in 3 until 20
    } catch (e: NumberFormatException) {
        false
    }

    ProvideTextStyle(TextStyle(color = Color.White)) {
        Column(
            Modifier
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
            Column(
                Modifier
                    .width(300.dp)
                    .border(
                        BorderStroke(1.dp, SolidColor(MaterialTheme.colors.primary)),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(10.dp)
            ) {
                RadioSelection(playModes.keys.toList(), selected = selectedPlayMode, onOptionSelect = { selectedPlayMode = it })

                Divider(
                    Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(MaterialTheme.colors.primary)
                )

                RadioSelection(gameModes.keys.toList(), selected = selectedGameMode, onOptionSelect = { selectedGameMode = it })

                // TODO: stop copying this
                Divider(
                    Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(MaterialTheme.colors.primary)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = recordGame, onCheckedChange = { checked -> recordGame = checked })
                    Text("Record Game?")
                }
                Divider(
                    Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(MaterialTheme.colors.primary)
                )
                RestrictedInputField(
                    { value, onValueChange ->
                        TextField(
                            value,
                            label = { Text("Board Size") },
                            onValueChange = onValueChange,
                            maxLines = 1,
                            isError = !boardSizeIsValid
                        )
                    },
                    value = boardSizeInput,
                    isAllowed = makeCharsetValidator("0123456789"),
                    onValueChange = { boardSizeInput = it },
                )
            }
            Button(
                onClick = {
                    onGameStart(playModes[selectedPlayMode]!!, gameModes[selectedGameMode]!!, boardSizeInput.toInt(), recordGame)
                },
                enabled = boardSizeIsValid,
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
            ) { Text("New Game") }
        }
    }
}