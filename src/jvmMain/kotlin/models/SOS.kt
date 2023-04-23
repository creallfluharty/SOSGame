package models

data class SOS<PID>(val start: RectCoordinate, val end: RectCoordinate, val player: PID)
