package components

import models.PlayAction
import models.Player
import models.PlayerID
import models.ScreenState


data class GameReplayState private constructor(
    val boardState: RectangularBoardState,
    val players: List<Player>,
    private val history: List<Pair<PlayAction, PlayerID>>,
    val turn: Int,
) : ScreenState {
    constructor(width: Int, height: Int, players: List<Player>, history: List<Pair<PlayAction, PlayerID>>) : this(
        RectangularBoardState(width, height),
        players,
        history,
        0,
    )
    fun checkCanAdvance() = turn < history.size

    fun advance() =
        if (!checkCanAdvance()) null
        else when (val p = history[turn].first) {
            is PlayAction.PlaceTile -> copy(
                boardState = boardState.placeToken(p.what, p.r, p.c, history[turn].second).second,
                turn = turn + 1,
            )
        }

    // TODO: retreat() (requires adding unplaceTile() to GameBoard though)

    fun getHistoryPrintout() = history.map {
        val (action, pid) = it
        return@map when (action) {
            is PlayAction.PlaceTile -> "Player ${pid} placed a(n) ${action.what.letter} at (${action.r}, ${action.c})."
        }
    }.joinToString("\n")
}