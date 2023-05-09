package screens

import models.GameState
import models.ScreenState

data class GameViewState(val gameState: GameState) : ScreenState {
    fun updateGameState(gameState: GameState) = copy(gameState = gameState)
}