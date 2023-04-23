package components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import helpers.TextDrawer
import models.Player


@Composable
fun RectangularBoard(
    cellRadius: Int,
    boardState: RectangularBoardState,
    players: List<Player>,
    onTilePressed: (r: Int, c: Int) -> Unit,
) {
    val cellDiameter = 2 * cellRadius;
    val realWidth = cellDiameter * boardState.width
    val realHeight = cellDiameter * boardState.height

    val onClickHandler by rememberUpdatedState { pos: Offset ->
        val r = (pos.y / cellDiameter).toInt()
        val c = (pos.x / cellDiameter).toInt()
        onTilePressed(r, c)
    }
    val textDrawer = TextDrawer.makeWithDefaultTextMeasurer()

    // TODO: maybe account for width of lines?
    // TODO: padding?
    Canvas(Modifier
        .width(realWidth.dp)
        .height(realHeight.dp)
        .pointerInput(Unit) { detectTapGestures(onTap = { onClickHandler(it) }) }
    ) {
        for (r in 1 until boardState.height) {
            val y = (r * cellDiameter).toFloat()
            drawLine(Color.Black, Offset(0f, y), Offset(realWidth.toFloat(), y))
        }

        for (c in 1 until boardState.height) {
            val x = (c * cellDiameter).toFloat()
            drawLine(Color.Black, Offset(x, 0f), Offset(x, realHeight.toFloat()))
        }

        for (r in 0 until boardState.height) {
            for (c in 0 until boardState.width) {
                val token = boardState.getToken(r, c)
                if (token != null) {
                    val x = (c * cellDiameter).toFloat() + cellRadius
                    val y = (r * cellDiameter).toFloat() + cellRadius
                    token.draw(this, textDrawer, cellRadius, Offset(x, y))
                }
            }
        }

        for (run in boardState.getSOSs()) {
            // TODO: Less duplication
            val x1 = (run.start.c * cellDiameter).toFloat() + cellRadius
            val y1 = (run.start.r * cellDiameter).toFloat() + cellRadius
            val x2 = (run.end.c * cellDiameter).toFloat() + cellRadius
            val y2 = (run.end.r * cellDiameter).toFloat() + cellRadius
            drawLine(players[run.player].color, Offset(x1, y1), Offset(x2, y2))
        }
    }
}