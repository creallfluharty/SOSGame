

import components.AlreadyOccupiedException
import components.InvalidBoardSizeException
import components.RectangularBoardState
import components.Token
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class RectangularBoardStateTest {
    @Test
    fun testCreateBoardWithInvalidSmallSize() {
        assertThrows(InvalidBoardSizeException::class.java) {
            RectangularBoardState(2, 2)
        }
    }

    @Test
    fun testCreateBoardWithInvalidLargeSize() {
        assertThrows(InvalidBoardSizeException::class.java) {
            RectangularBoardState(21, 21)
        }
    }

    @Test
    fun testCreateBoardWithValidSize() {
        val board = RectangularBoardState(3, 3)
        assertEquals(board.width, 3)
        assertEquals(board.height, 3)
    }

    @Test
    fun testPlaceTokenUnoccupied() {
        val board = RectangularBoardState(3, 3)
        val token = Token('S')
        val newBoard = board.placeToken(token, 1, 1, 1)
        assertEquals(newBoard.getToken(1, 1), token)
    }

    @Test
    fun testPlaceTokenOccupied() {
        var board = RectangularBoardState(3, 3)
        val token = Token('S')
        board = board.placeToken(token, 1, 1, 1)

        assertThrows(AlreadyOccupiedException::class.java) {
            board.placeToken(token, 1, 1, 1)
        }
    }

    @Test
    fun testPlaceTokenOffBoard() {
        val board = RectangularBoardState(3, 3)
        val token = Token('S')

        assertThrows(IndexOutOfBoundsException::class.java) {
            board.placeToken(token, 3, 3, 1)
        }
    }
}