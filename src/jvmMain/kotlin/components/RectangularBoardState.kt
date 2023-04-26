package components

import models.PlayerID
import models.RectCoordinate
import models.RectDirection
import models.SOS

class AlreadyOccupiedException(message: String) : RuntimeException(message)

class InvalidBoardSizeException(message: String) : RuntimeException(message)


class RectangularBoardState private constructor(
    val width: Int,
    val height: Int,
    private val matrix: List<List<Token?>>,
    private val SOSs: List<SOS<Int>>,
    private val unfilledTiles: Int,
) {
    constructor(width: Int, height: Int) : this(
        width,
        height,
        List(height) { List<Token?>(width) { null } },
        listOf(),
        width * height,
    ) {
        if (width !in 3..20 || height !in 3..20) {
            throw InvalidBoardSizeException("Board size $width $height is invalid!")
        }
    }

    fun inBounds(r: Int, c: Int) =
        r in 0 until height && c in 0 until width

    fun placeToken(token: Token, r: Int, c: Int, playerID: PlayerID): Pair<Int, RectangularBoardState> {
        if (!inBounds(r, c))
            throw IndexOutOfBoundsException("$r $c is not in bounds!")

        if (matrix[r][c] != null)
            throw AlreadyOccupiedException("$r $c is already occupied!")

        val newSOSs = getSOSFormedByPlacement(r, c, token, playerID)

        val newBoard = RectangularBoardState(
            width,
            height,
            matrix.mapIndexed { rowIx, row ->
                if (rowIx != r) row
                else row.mapIndexed { colIx, oldToken -> if (colIx != c) oldToken else token }
            },
            SOSs + newSOSs,
            unfilledTiles - 1,
        )

        return Pair(newSOSs.size, newBoard);
    }

    fun getToken(row: Int, column: Int) =
        if (!inBounds(row, column))
            throw IndexOutOfBoundsException("$row $column is not in bounds!")
        else
            matrix[row][column]

    fun getSOSFormedByPlacement(r: Int, c: Int, what: Token, playerID: PlayerID): List<SOS<PlayerID>> {
        // TODO: Use partial run algorithm instead of this mess
        val coordinate = RectCoordinate(r, c)
        val newRuns = mutableListOf<SOS<Int>>()

        fun safeGetToken(coord: RectCoordinate) =
            if (inBounds(coord.r, coord.c)) matrix[coord.r][coord.c]
            else null

        when (what.letter) {
            'O' -> {
                for (dir in RectDirection.PRIMARY_DIRECTIONS) {
                    val start = coordinate + dir
                    val end = coordinate - dir
                    if (safeGetToken(start)?.letter == 'S' && safeGetToken(end)?.letter == 'S')
                        newRuns.add(SOS(start, end, playerID))
                }
            }
            'S' -> {
                for (dir in RectDirection.ALL_DIRECTIONS) {
                    val oPos = coordinate + dir
                    val sPos = oPos + dir

                    if (safeGetToken(oPos)?.letter == 'O' && safeGetToken(sPos)?.letter == 'S')
                        newRuns.add(SOS(coordinate, sPos, playerID))
                }
            }
            else -> {
                throw AssertionError("We're playing SOS - for now anyway")
            }
        }

        return newRuns
    }

    fun getSOSs() = SOSs

    fun isFull() = unfilledTiles == 0
}