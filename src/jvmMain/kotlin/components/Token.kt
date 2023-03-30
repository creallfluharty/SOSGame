package components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText

class Token (val letter: Char) {
    @OptIn(ExperimentalTextApi::class)
    fun draw(drawScope: DrawScope, textMeasurer: TextMeasurer, cellRadius: Int, center: Offset) {
        val textSize = textMeasurer.measure(AnnotatedString(letter.toString())).size
        drawScope.apply {
            drawCircle(Color.Red, cellRadius.toFloat(), center)
            drawText(
                textMeasurer,
                letter.toString(),
                topLeft = Offset(center.x - textSize.width / 2, center.y - textSize.height / 2)
            )
        }
    }
}