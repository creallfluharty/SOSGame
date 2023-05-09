package models

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import components.RectangularBoard
import components.TokenSelector
import components.TokenSelectorState

typealias PlayerID = Int

data class HumanPlayer private constructor(
    private val id: Int,
    private val color: Color,
    private val score: PlayerID,
    private val tokenSelectorState: TokenSelectorState
) : Player {

    constructor(id: Int, color: Color) : this(id, color, 0, TokenSelectorState(GeneralGameState.ALLOWED_TOKENS, 0))

    override fun getID() = id
    override fun getColor() = color
    override fun getScore() = score

    override fun addToScore(points: Int) = copy(score = score + points)

    @Composable
    override fun playerInterface(
        gameState: GameState,
        cellRadius: Int,
        setPlayerState: (Player) -> Unit,
        makeAction: (PlayAction) -> Unit,
    ) {
        Row {
            TokenSelector(cellRadius, 1, tokenSelectorState) {
                setPlayerState(copy(tokenSelectorState = tokenSelectorState.setSelectedIndex(it)))
            }
            RectangularBoard(cellRadius, gameState.getBoardState(), gameState.getPlayers()) { r, c ->
                makeAction(PlayAction.PlaceTile(r, c, tokenSelectorState.getSelection()))
            }
        }
    }
}