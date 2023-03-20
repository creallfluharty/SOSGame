import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

// TODO: This is pointless, figure out a better way to test acceptance criteria
class ScreenTest {
    @Test
    fun testCreateGameWithGameModeAndPlayMode() {
        val screenState = Screen.MainGame(PlayMode.PassAndPlay, GameMode.General, 3, true)
        assertEquals(screenState.playMode, PlayMode.PassAndPlay)
        assertEquals(screenState.gameMode, GameMode.General)
        assertEquals(screenState.boardSize, 3)
        assertEquals(screenState.recordGame, true)
    }
}