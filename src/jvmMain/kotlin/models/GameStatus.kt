package models

sealed class GameStatus {
    data class Ongoing(val turn: PlayerID) : GameStatus()
    data class Won(val winner: PlayerID) : GameStatus()
    data class Tie(val players: List<PlayerID>) : GameStatus()
}