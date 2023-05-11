
import androidx.compose.ui.graphics.Color
import components.RectangularBoardState
import components.Token
import models.ComputerPlayer
import models.PlayAction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class ComputerPlayerTest {
    @Test
    fun testComputerPlayerMakesMove() {
        val player = ComputerPlayer(1, Color.Red)
        val boardState = RectangularBoardState(3, 3)

        assertDoesNotThrow {
            val move = player.getMove(boardState)
        }
    }

    @Test
    fun testComputerPlayerMakesBestMove() {
        val player = ComputerPlayer(1, Color.Red)
        var boardState = RectangularBoardState(3, 3)
            .placeToken(Token('S'), 0, 2, 0).second
            .placeToken(Token('O'), 1, 1, 0).second

        val move = player.getMove(boardState)
        assertEquals(move, PlayAction.PlaceTile(2, 0, Token('S')))
    }
}