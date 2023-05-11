import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import screens.GameMode
import screens.GameSetupState
import screens.PlayerType

class GameSetupStateTest {
    @Test
    fun successfulBoardSizeSelection() {
        var state = GameSetupState()
        val expectedSize = 10

        state = state.replaceBoardSizeInput("$expectedSize")

        assertTrue(state.checkBoardSizeIsValid())
        assertEquals(expectedSize, state.getBoardSize())
    }
    @Test
    fun unsuccessfulBoardSizeSelection() {
        var state = GameSetupState()

        state = state.replaceBoardSizeInput("25")

        assertFalse(state.checkBoardSizeIsValid())
    }

    @Test
    fun successfulGameModeSelection() {
        var state = GameSetupState()
        val expectedMode = GameMode.General

        state = state.selectGameMode(expectedMode)

        assertEquals(expectedMode, state.getSelectedGameMode())
    }

    @Test
    fun successfulHumanPlayerSelection() {
        val state = GameSetupState()
        assertEquals(PlayerType.Human, state.getPlayer1Type())
    }

    @Test
    fun successfulComputerPlayerSelection() {
        var state = GameSetupState()
        state = state.togglePlayer1Type()
        assertEquals(PlayerType.Computer, state.getPlayer1Type())
    }
}