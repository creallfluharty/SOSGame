package components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import helpers.TextDrawer

data class Token(val letter: Char) {
    fun draw(drawScope: DrawScope, textDrawer: TextDrawer, cellRadius: Int, center: Offset) {
        drawScope.apply {
            drawCircle(Color.Yellow, cellRadius.toFloat(), center)
            textDrawer.drawCenteredText(this, letter.toString(), center)
        }
    }
}