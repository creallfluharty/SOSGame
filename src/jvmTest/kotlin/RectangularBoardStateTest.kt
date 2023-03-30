import components.AlreadyOccupiedException
import components.InvalidBoardSizeException
import components.RectangularBoardState
import components.Token
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class RectangularBoardStateTest {
    @Test
    fun testCreateBoardWithInvalidSmallSize() {
        assertThrows(InvalidBoardSizeException::class.java) {
            RectangularBoardState<Token>(2, 2)
        }
    }

    @Test
    fun testCreateBoardWithInvalidLargeSize() {
        assertThrows(InvalidBoardSizeException::class.java) {
            RectangularBoardState<Token>(21, 21)
        }
    }

    @Test
    fun testCreateBoardWithValidSize() {
        val board = RectangularBoardState<Token>(3, 3)
        assertEquals(board.width, 3)
        assertEquals(board.height, 3)
    }

    @Test
    fun testPlaceTileUnoccupied() {
        val board = RectangularBoardState<Token>(3, 3)
        val tile = Token('S')
        val newBoard = board.placeTile(tile, 1, 1, 1)
        assertEquals(newBoard.getTile(1, 1), tile)
    }

    @Test
    fun testPlaceTileOccupied() {
        var board = RectangularBoardState<Token>(3, 3)
        val tile = Token('S')
        board = board.placeTile(tile, 1, 1, 1)

        assertThrows(AlreadyOccupiedException::class.java) {
            board.placeTile(tile, 1, 1, 1)
        }
    }

    @Test
    fun testPlaceTileOffBoard() {
        val board = RectangularBoardState<Token>(3, 3)
        val tile = Token('S')

        assertThrows(IndexOutOfBoundsException::class.java) {
            board.placeTile(tile, 3, 3, 1)
        }
    }
}