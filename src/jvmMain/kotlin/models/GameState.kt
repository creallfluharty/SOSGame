package models

import components.RectangularBoardState
import components.Token
import components.TokenSelectorState


// TODO: After this assignment is due, make this an implementation and pass an EndConditionChecker instead of having separate classes
interface GameState<S: GameState<S>> {
    fun getTurn(): PlayerID
    fun getBoardState(): RectangularBoardState
    fun getTokenSelectorState(): TokenSelectorState
    fun getPlayers(): List<Player>
    fun getStatus(): GameStatus
    fun checkGameEnded(): Boolean

    fun getSelectedToken(): Token
    fun setTokenSelectionIndex(index: Int): S

    fun placeToken(token: Token, r: Int, c: Int, playerID: PlayerID): S
}