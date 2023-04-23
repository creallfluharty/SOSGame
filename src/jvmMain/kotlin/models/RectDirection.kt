package models

data class RectDirection(val dr: Int, val dc: Int) {
    companion object {
        val PRIMARY_DIRECTIONS = listOf(
            RectDirection(1, 0),
            RectDirection(1, 1),
            RectDirection(0, 1),
            RectDirection(-1, 1),
        )
        val ALL_DIRECTIONS = PRIMARY_DIRECTIONS + PRIMARY_DIRECTIONS.map { -it }
    }

    operator fun unaryMinus() = RectDirection(-dr, -dc)
}