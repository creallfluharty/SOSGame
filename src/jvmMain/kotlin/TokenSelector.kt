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
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import kotlin.math.abs
import kotlin.random.Random

// TODO: Drag and drop? for now we'll just highlight

@OptIn(ExperimentalTextApi::class)
@Composable
fun TokenSelector(
    tokens: List<Token>,
    cellRadius: Int,
    highlightRadius: Int,
    selectedIndex: Int,
    setSelectedIndex: (Int) -> Unit
) {
    val optionRadius = cellRadius + highlightRadius
    val optionDiameter = 2 * optionRadius

    fun onClickHandler(pos: Offset) {
        val ix = (pos.y / optionDiameter).toInt()
        // TODO: Fix this
//        if (abs(pos.y - (ix * optionDiameter - optionRadius)) <= cellRadius)
        setSelectedIndex(ix)
    }
    val textMeasurer = rememberTextMeasurer()

    Canvas(Modifier
        .height((optionDiameter * tokens.size).dp)
        .width(optionDiameter.dp)
        .pointerInput(Unit) { detectTapGestures(onTap = { onClickHandler(it) }) }
    ) {
        // TODO: add parameter to pick vertical/horizontal
        for ((r, token) in tokens.withIndex()) {
            val x = optionRadius.toFloat()
            val y = (r * optionDiameter).toFloat() + optionRadius
            token.draw(this, textMeasurer, cellRadius, Offset(x, y))
            if (r == selectedIndex)
                drawCircle(
                    Color.Black,
                    center = Offset(x, y),
                    radius = optionRadius.toFloat() - highlightRadius / 2,
                    style = Stroke(highlightRadius.toFloat())
                )
        }
    }
}