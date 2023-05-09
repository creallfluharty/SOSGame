package models

import components.RectangularBoardState


// TODO: After this assignment is due, make this an implementation and pass an EndConditionChecker instead of having separate classes
interface GameState {
    fun getModeString(): String
    fun getTurn(): PlayerID
    fun getBoardState(): RectangularBoardState
    fun getPlayers(): List<Player>
    fun getStatus(): GameStatus
    fun checkGameEnded(): Boolean
    fun getHistory(): List<Pair<PlayAction, PlayerID>>

    fun updatePlayer(id: PlayerID, newPlayer: Player): GameState

    fun placeToken(placement: PlayAction.PlaceTile, playerID: PlayerID): GameState
}