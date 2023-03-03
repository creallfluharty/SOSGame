import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


enum class TileType {
    S,
    O,
}

data class Node(
    val tileType: TileType? = null,
    val horizontalAlts: Int = 0,
    val ascendingAlts: Int = 0,
    val verticalAlts: Int = 0,
    val descendingAlts: Int = 0,
)

data class BoardState(val matrix: List<List<Node>>) {
    fun placeTile(tile: TileType, r: Int, c: Int): BoardState {
        return BoardState(matrix.mapIndexed { rowIx, row ->
            // TODO: Don't map rows that don't change
            row.mapIndexed { colIx, node ->
                if (rowIx == r && colIx == c) node.copy(tileType = tile) else node
            }
        })
    }
}


@Composable
@Preview
fun LetterTile(letter: String, size: Dp, borderWidth: Dp = 1.dp, borderRadiusPercent: Int = 25) {
    val shape = RoundedCornerShape(borderRadiusPercent)

    Column(
        Modifier
            .width(size)
            .height(size)
            .border(BorderStroke(borderWidth, Color.Black), shape = shape)
            .background(MaterialTheme.colors.secondary, shape = shape),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(letter)
    }
}

@Composable
@Preview
fun LetterPickerTile(letters: Pair<String, String>, size: Dp, borderWidth: Dp = 1.dp, borderRadiusPercent: Int = 25) {
    //
}

@Composable
@Preview
fun SOSBoard(width: Int, height: Int) {
    var boardState by remember { mutableStateOf(BoardState(List(height) { List(width) { Node() } })) }

    Column(
        Modifier
            .width(IntrinsicSize.Min)
//            .drawWithContent {
//                drawContent()
//                drawLine(Color.Red, Offset(15f, 15f), Offset(32*3+15f, 32*3+15f), 1f)
//                drawLine(Color.Red, Offset(32*3+15f, 15f), Offset(15f, 32*3+15f), 1f)
//            }
    ) {
        IntRange(1, height).map { rowNum ->
            Row(Modifier.height(IntrinsicSize.Min)) {
                IntRange(1, width).map { colNum ->
                    val tile = boardState.matrix[rowNum - 1][colNum - 1].tileType
                    Box(
                        Modifier
                            .clickable {
                                if (tile == null)
                                    boardState = boardState.placeTile(TileType.S, rowNum - 1, colNum - 1)
                            }
                            .padding(5.dp)
                            .width(20.dp)
                            .height(20.dp)
                    ) {
                        if (tile != null)
                            LetterTile(if (tile == TileType.S) "S" else "O", 25.dp)
                    }
                    if (colNum < width) Divider(Modifier.fillMaxHeight().width(1.dp).background(Color.Black))
                }
            }
            if (rowNum < height) Divider(Modifier.fillMaxWidth().height(1.dp).background(Color.Black))
        }
    }
}

@Composable
@Preview
fun MainGame(gameMode: GameMode, boardSize: Int, recordGame: Boolean) {

    Column {
        SOSBoard(boardSize, boardSize)
    }
}