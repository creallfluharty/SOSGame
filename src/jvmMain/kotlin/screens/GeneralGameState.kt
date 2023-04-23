package screens

import androidx.compose.ui.graphics.Color
import components.RectangularBoardState
import components.Token
import components.TokenSelectorState
import models.Player
import models.PlayerID

data class GeneralGameState private constructor(
    val playMode: PlayMode,
    val gameMode: GameMode,
    val recordGame: Boolean,
    val boardState: RectangularBoardState,
    val tokenSelectorState: TokenSelectorState,
    val players: List<Player>,
    val turn: PlayerID,
) : ScreenState {

    companion object {
        val ALLOWED_TOKENS = listOf(Token('S'), Token('O'))
    }

    constructor(
        playMode: PlayMode,
        gameMode: GameMode,
        boardSize: Int,
        recordGame: Boolean,
    ) : this (
        playMode,
        gameMode,
        recordGame,
        RectangularBoardState(boardSize, boardSize),
        TokenSelectorState(ALLOWED_TOKENS, 0),
        listOf(Player(0, Color.Red), Player(1, Color.Blue)), // TODO
        0,
    )

    fun setTokenSelectionIndex(index: Int) = copy(tokenSelectorState = tokenSelectorState.setSelectedIndex(index))
    fun getSelectedToken() = tokenSelectorState.getSelection()

    fun placeToken(token: Token, r: Int, c: Int, playerID: PlayerID) = boardState.placeToken(token, r, c, playerID).let {
        copy(
            boardState = it.second,
            players = players.map { player -> if (player.id == playerID) player.addToScore(it.first) else player},
            turn = (turn + 1) % players.size,
        )
    }
}