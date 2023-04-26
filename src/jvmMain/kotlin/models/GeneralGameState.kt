package models

import androidx.compose.ui.graphics.Color
import components.RectangularBoardState
import components.Token
import components.TokenSelectorState

data class GeneralGameState private constructor(
    private val boardState: RectangularBoardState,
    private val tokenSelectorState: TokenSelectorState,
    private val players: List<Player>,
    private val turn: PlayerID,
) : ScreenState, GameState<GeneralGameState> {

    companion object {
        val ALLOWED_TOKENS = listOf(Token('S'), Token('O'))
    }

    constructor(
        boardSize: Int,
    ) : this (
        RectangularBoardState(boardSize, boardSize),
        TokenSelectorState(ALLOWED_TOKENS, 0),
        listOf(Player(0, Color.Red), Player(1, Color.Blue)), // TODO
        0,
    )

    override fun setTokenSelectionIndex(index: Int) = copy(tokenSelectorState = tokenSelectorState.setSelectedIndex(index))
    override fun getSelectedToken() = tokenSelectorState.getSelection()

    override fun placeToken(token: Token, r: Int, c: Int, playerID: PlayerID) = boardState.placeToken(token, r, c, playerID).let {
        copy(
            boardState = it.second,
            players = players.map { player -> if (player.id == playerID) player.addToScore(it.first) else player},
            turn = (turn + 1) % players.size,
        )
    }

    fun getHighScore(): List<PlayerID> {
        val winners = mutableListOf<PlayerID>()
        var highScore = 0
        for (player in players) {
            if (player.score > highScore) {
                winners.clear()
                winners.add(player.id)
                highScore = player.score
            } else if (player.score == highScore)
                winners.add(player.id)
        }
        return winners
    }

    override fun getTurn() = turn

    override fun getBoardState() = boardState

    override fun getTokenSelectorState() = tokenSelectorState

    override fun getPlayers() = players

    override fun checkGameEnded() = boardState.isFull()

    override fun getStatus() =
        if (checkGameEnded())
            getHighScore().let { if (it.size == 1) GameStatus.Won(it[0]) else GameStatus.Tie(it) }
        else
            GameStatus.Ongoing(turn)
}