package models

import components.Token


data class SimpleGameState  private constructor(
    val generalGameState: GeneralGameState
) : ScreenState, GameState<SimpleGameState> {
    constructor(boardSize: Int) : this(GeneralGameState(boardSize))

    override fun setTokenSelectionIndex(index: Int) =
        copy(generalGameState = generalGameState.setTokenSelectionIndex(index))

    override fun getSelectedToken() = generalGameState.getSelectedToken()

    override fun placeToken(token: Token, r: Int, c: Int, playerID: PlayerID) =
        copy(generalGameState = generalGameState.placeToken(token, r, c, playerID))

    override fun getTurn() = generalGameState.getTurn()

    override fun getBoardState() = generalGameState.getBoardState()

    override fun getTokenSelectorState() = generalGameState.getTokenSelectorState()

    override fun getPlayers() = generalGameState.getPlayers()

    override fun checkGameEnded() = generalGameState.getBoardState().isFull()
            || generalGameState.getBoardState().getSOSs().isNotEmpty() // TODO: Demeter?

    override fun getStatus() =
        if (checkGameEnded())
            generalGameState.getHighScore().let { if (it.size == 1) GameStatus.Won(it[0]) else GameStatus.Tie(it) }
        else
            GameStatus.Ongoing(generalGameState.getTurn())
}