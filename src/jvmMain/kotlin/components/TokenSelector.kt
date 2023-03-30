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
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import helpers.TextDrawer

@Composable
fun TokenSelector(
    cellRadius: Int,
    highlightRadius: Int,
    state: TokenSelectorState,
    onTokenIndexSelection: (Int) -> Unit,
) {
    val optionRadius = cellRadius + highlightRadius
    val optionDiameter = 2 * optionRadius

    val onClickHandler by rememberUpdatedState { pos: Offset ->
        val ix = (pos.y / optionDiameter).toInt()
        // TODO: Ignore clicks between the tokens
        onTokenIndexSelection(ix)
    }

    val textDrawer = TextDrawer.makeWithDefaultTextMeasurer()

    Canvas(Modifier
        .height((optionDiameter * state.getNumTokens()).dp)
        .width(optionDiameter.dp)
        .pointerInput(Unit) { detectTapGestures(onTap = { onClickHandler(it) }) }
    ) {
        // TODO: Maybe add parameter to pick vertical/horizontal?
        for ((r, token, isSelected) in state.getOptionIter()) {
            val x = optionRadius.toFloat()
            val y = (r * optionDiameter).toFloat() + optionRadius
            token.draw(this, textDrawer, cellRadius, Offset(x, y))
            if (isSelected)
                drawCircle(
                    Color.Black,
                    center = Offset(x, y),
                    radius = optionRadius.toFloat() - highlightRadius / 2,
                    style = Stroke(highlightRadius.toFloat())
                )
        }
    }
}