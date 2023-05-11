

import components.AlreadyOccupiedException
import components.RectangularBoardState
import components.Token
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class RectangularBoardStateTest {
    @Test
    fun successfulTokenPlacement() {
        val board = RectangularBoardState(3, 3)
        val token = Token('S')
        val (_, newBoard) = board.placeToken(token, 1, 1, 1)
        assertEquals(newBoard.getToken(1, 1), token)
    }

    @Test
    fun unsuccessfulOccupiedTokenPlacement() {
        var board = RectangularBoardState(3, 3)
        val token = Token('S')
        board = board.placeToken(token, 1, 1, 1).second

        assertThrows(AlreadyOccupiedException::class.java) {
            board.placeToken(token, 1, 1, 1)
        }
    }

    @Test
    fun unsuccessfulOffBoardTokenPlacement() {
        val board = RectangularBoardState(3, 3)
        val token = Token('S')

        assertThrows(IndexOutOfBoundsException::class.java) {
            board.placeToken(token, 3, 3, 1)
        }
    }
}