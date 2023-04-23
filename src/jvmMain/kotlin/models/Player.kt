package models

import androidx.compose.ui.graphics.Color

typealias PlayerID = Int

data class Player private constructor(val id: Int, val color: Color, val score: PlayerID) {
    constructor(id: Int, color: Color) : this(id, color, 0)

    fun addToScore(points: Int) = copy(score = score + points)
}