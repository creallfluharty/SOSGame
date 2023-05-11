import androidx.compose.ui.graphics.Color
import components.Token
import models.GameStatus
import models.GeneralGameState
import models.HumanPlayer
import models.PlayAction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GeneralGameStateTest {
    @Test
    fun generalGameWon() {
        var state = GeneralGameState(3, listOf(HumanPlayer(0, Color.Red), HumanPlayer(1, Color.Blue)))
        state = state.placeToken(PlayAction.PlaceTile(0, 0, Token('S')), 0)
        state = state.placeToken(PlayAction.PlaceTile(0, 1, Token('S')), 1)
        state = state.placeToken(PlayAction.PlaceTile(0, 2, Token('S')), 0)
        state = state.placeToken(PlayAction.PlaceTile(1, 0, Token('S')), 1)
        state = state.placeToken(PlayAction.PlaceTile(1, 2, Token('S')), 0)
        state = state.placeToken(PlayAction.PlaceTile(2, 0, Token('S')), 1)
        state = state.placeToken(PlayAction.PlaceTile(2, 1, Token('S')), 0)
        state = state.placeToken(PlayAction.PlaceTile(2, 2, Token('S')), 1)
        state = state.placeToken(PlayAction.PlaceTile(1, 1, Token('O')), 0)

        assertEquals(state.getStatus(), GameStatus.Won(state.getPlayers()[0]))
    }

    @Test
    fun generalGameTie() {
        var state = GeneralGameState(3, listOf(HumanPlayer(0, Color.Red), HumanPlayer(1, Color.Blue)))
        state = state.placeToken(PlayAction.PlaceTile(0, 0, Token('S')), 0)
        state = state.placeToken(PlayAction.PlaceTile(0, 1, Token('S')), 1)
        state = state.placeToken(PlayAction.PlaceTile(0, 2, Token('S')), 0)
        state = state.placeToken(PlayAction.PlaceTile(1, 0, Token('S')), 1)
        state = state.placeToken(PlayAction.PlaceTile(1, 1, Token('S')), 0)
        state = state.placeToken(PlayAction.PlaceTile(1, 2, Token('S')), 1)
        state = state.placeToken(PlayAction.PlaceTile(2, 0, Token('S')), 0)
        state = state.placeToken(PlayAction.PlaceTile(2, 1, Token('S')), 1)
        state = state.placeToken(PlayAction.PlaceTile(2, 2, Token('S')), 0)

        assertEquals(state.getStatus(), GameStatus.Tie(state.getPlayers()))
    }
}