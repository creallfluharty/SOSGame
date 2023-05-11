
import androidx.compose.ui.graphics.Color
import components.Token
import models.GameStatus
import models.HumanPlayer
import models.PlayAction
import models.SimpleGameState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SimpleGameStateTest {
    @Test
    fun simpleGameWon() {
        var state = SimpleGameState(3, listOf(HumanPlayer(0, Color.Red), HumanPlayer(1, Color.Blue)))
        state = state.placeToken(PlayAction.PlaceTile(0, 0, Token('S')), 0)
        state = state.placeToken(PlayAction.PlaceTile(0, 1, Token('O')), 1)
        state = state.placeToken(PlayAction.PlaceTile(0, 2, Token('S')), 0)

        assertEquals(state.getStatus(), GameStatus.Won(state.getPlayers()[0]))
    }

    @Test
    fun simpleGameTie() {
        var state = SimpleGameState(3, listOf(HumanPlayer(0, Color.Red), HumanPlayer(1, Color.Blue)))
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