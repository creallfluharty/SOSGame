package models

data class RectCoordinate(val r: Int, val c: Int) {
    operator fun plus(direction: RectDirection) =
        RectCoordinate(r + direction.dr, c + direction.dc)

    operator fun minus(direction: RectDirection) =
        RectCoordinate(r - direction.dr, c - direction.dc)

    operator fun minus(other: RectCoordinate) =
        RectDirection(r - other.r, c - other.c)
}