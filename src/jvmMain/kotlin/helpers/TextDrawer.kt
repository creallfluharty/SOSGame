package helpers

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.*

@OptIn(ExperimentalTextApi::class)
class TextDrawer(
    private val textMeasurer: TextMeasurer,
) {
    companion object {
        @Composable
        fun makeWithDefaultTextMeasurer(): TextDrawer {
            return TextDrawer(rememberTextMeasurer())
        }
    }

    fun drawCenteredText(drawScope: DrawScope, text: String, offset: Offset) {
        val textSize = textMeasurer.measure(AnnotatedString(text)).size
        drawScope.drawText(
            textMeasurer,
            text,
            topLeft = Offset(offset.x - textSize.width / 2, offset.y - textSize.height / 2)
        )
    }
}