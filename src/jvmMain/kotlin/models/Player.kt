package models

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

interface Player {
    fun getID(): PlayerID
    fun getColor(): Color
    fun getScore(): Int

    fun addToScore(points: Int): Player

    // TODO: It would probably be better to just have some onEvent function that accepts TurnStart, TokenSelected, and TilePressed
    @Composable
    fun playerInterface(
        gameState: GameState,
        cellRadius: Int,
        setPlayerState: (Player) -> Unit,
        makeAction: (PlayAction) -> Unit,
    )
}