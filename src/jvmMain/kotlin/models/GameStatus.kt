package models

sealed class GameStatus {
    data class Ongoing(val turn: Player) : GameStatus()
    data class Won(val winner: Player) : GameStatus()
    data class Tie(val players: List<Player>) : GameStatus()
}