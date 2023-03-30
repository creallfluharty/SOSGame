package models

interface Walker<CoordinateType> {
    fun next(coord: CoordinateType): CoordinateType
    fun previous(coord: CoordinateType): CoordinateType
}