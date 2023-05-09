package models

import components.RectangularBoardState
import components.Token
import helpers.maxByList

data class GeneralGameState private constructor(
    private val boardState: RectangularBoardState,
    private val players: List<Player>,
    private val turn: PlayerID,
    private val history: List<Pair<PlayAction, PlayerID>>,
) : GameState {

    // TODO move this to interface?
    companion object {
        val ALLOWED_TOKENS = listOf(Token('S'), Token('O'))
    }

    constructor(
        boardSize: Int,
        players: List<Player>,
    ) : this (
        RectangularBoardState(boardSize, boardSize),
        players,
        0,
        listOf(),
    )

    override fun getModeString() = "General"

    // TODO: Assert playerID == turn
    override fun placeToken(placement: PlayAction.PlaceTile, playerID: PlayerID) =
        boardState.placeToken(placement.what, placement.r, placement.c, playerID).let {
            copy(
                boardState = it.second,
                players = players.map { player -> if (player.getID() == playerID) player.addToScore(it.first) else player},
                turn = (turn + 1) % players.size,
                history = history + listOf(Pair(placement, playerID)),
            )
        }

    fun getHighScore() = maxByList(players) { it.getScore() }

    override fun getTurn() = turn

    override fun getBoardState() = boardState

    override fun getPlayers() = players

    override fun checkGameEnded() = boardState.isFull()

    override fun getHistory() = history

    override fun updatePlayer(id: PlayerID, newPlayer: Player) =
        copy(players = players.map { player -> if (player.getID() == id) newPlayer else player})

    override fun getStatus() =
        if (checkGameEnded())
            getHighScore().let { if (it.size == 1) GameStatus.Won(it[0]) else GameStatus.Tie(it) }
        else
            GameStatus.Ongoing(players[turn])
}