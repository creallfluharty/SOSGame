package screens

import components.RectangularBoardState
import components.Token
import components.TokenSelectorState

data class MainGameState private constructor(
    private val playMode: PlayMode,
    private val gameMode: GameMode,
    private val recordGame: Boolean,
    val boardState: RectangularBoardState<Token>,
    val tokenSelectorState: TokenSelectorState,
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
        RectangularBoardState<Token>(boardSize, boardSize),
        TokenSelectorState(ALLOWED_TOKENS, 0)
    )

    fun getPlayMode() = playMode
    fun getGameMode() = gameMode
    fun getRecordGame() = recordGame

    fun setTokenSelectionIndex(index: Int) = copy(tokenSelectorState = tokenSelectorState.setSelectedIndex(index))
    fun placeTile(tile: Token, r: Int, c: Int, player: Int) = copy(boardState = boardState.placeTile(tile, r, c, player))
}