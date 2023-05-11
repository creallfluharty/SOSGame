package models

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import components.RectangularBoard
import components.RectangularBoardState
import helpers.maxByList
import kotlinx.coroutines.delay


data class ComputerPlayer private constructor(
    private val id: Int,
    private val color: Color,
    private val score: PlayerID,
) : Player {

    constructor(id: Int, color: Color) : this(id, color, 0)

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
        val move = getMove(gameState.getBoardState())
        RectangularBoard(cellRadius, gameState.getBoardState(), gameState.getPlayers()) { _, _ -> }
        LaunchedEffect(id) {
            delay(1000)
            makeAction(move)
        }
    }

    fun getMove(boardState: RectangularBoardState): PlayAction.PlaceTile {
        // TODO: If time permits using the partial-run algorithm, use that information to pick moves rather than this
        val moves = mutableListOf<PlayAction.PlaceTile>()

        for (r in 0 until boardState.height)
            for (c in 0 until boardState.width)
                if (boardState.getToken(r, c) == null)
                    for (token in GeneralGameState.ALLOWED_TOKENS)
                        moves.add(PlayAction.PlaceTile(r, c, token))

        val bestMoves = maxByList(moves) { boardState.getSOSFormedByPlacement(it.r, it.c, it.what, id).size }

        // TODO: handle case of empty list even though that shouldn't be possible anyway
        return bestMoves.random()
    }
}