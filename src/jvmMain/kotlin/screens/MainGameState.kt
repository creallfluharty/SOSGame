package screens

import components.RectangularBoardState
import components.Token
import components.TokenSelectorState

data class MainGameState private constructor(
    val playMode: PlayMode,
    val gameMode: GameMode,
    val recordGame: Boolean,
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

    fun setTokenSelectionIndex(index: Int) = copy(tokenSelectorState = tokenSelectorState.setSelectedIndex(index))
    fun getSelectedToken() = tokenSelectorState.getSelection()

    fun placeTile(tile: Token, r: Int, c: Int, player: Int) = copy(boardState = boardState.placeTile(tile, r, c, player))
}