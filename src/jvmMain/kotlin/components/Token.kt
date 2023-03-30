package components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import helpers.TextDrawer

class Token (val letter: Char) {
    fun draw(drawScope: DrawScope, textDrawer: TextDrawer, cellRadius: Int, center: Offset) {
        drawScope.apply {
            drawCircle(Color.Red, cellRadius.toFloat(), center)
            textDrawer.drawCenteredText(this, letter.toString(), center)
        }
    }
}