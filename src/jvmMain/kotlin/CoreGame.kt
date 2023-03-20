class AlreadyOccupiedException(message: String) : RuntimeException(message)

class InvalidBoardSizeException(message: String) : RuntimeException(message)

interface Walker<CoordinateType> {
    fun next(coord: CoordinateType): CoordinateType
    fun previous(coord: CoordinateType): CoordinateType
}

class Run<CoordinateType, WalkerType: Walker<CoordinateType>, PlayerType> private constructor (
    val start: CoordinateType,
    val end: CoordinateType,
    val walker: WalkerType,
    val winLength: Int,
    val length: Int,
    val winner: PlayerType?,
) {
    constructor(
        start: CoordinateType,
        walker: WalkerType,
        winLength: Int
    ) : this(
        start,
        walker.next(start),
        walker,
        winLength,
        1,
        null,
    )

    fun incrementStart(player: PlayerType): Run<CoordinateType, WalkerType, PlayerType> {
        if (length >= winLength)
            throw RuntimeException("Extending past winLength is undefined") // TODO: return multiple runs, or extra points?

        val winner = if (length + 1 == winLength) player else null

        return Run(
            walker.next(start),
            end,
            walker,
            winLength,
            length + 1,
            winner,
        )
    }

    fun incrementEnd(player: PlayerType): Run<CoordinateType, WalkerType, PlayerType> {
        if (length >= winLength)
            throw RuntimeException("Extending past winLength is undefined")  // TODO: return multiple runs, or extra points?

        val winner = if (length + 1 == winLength) player else null

        return Run(
            start,
            walker.next(end),
            walker,
            winLength,
            length + 1,
            winner,
        )
    }

    fun joinAtStart(other: Run<CoordinateType, WalkerType, PlayerType>, player: PlayerType): Run<CoordinateType, WalkerType, PlayerType> {
        if (walker.next(start) != other.walker.previous(other.end))
            throw RuntimeException("Runs cannot be joined")

        val newLength = length + other.length + 1

        if (newLength > winLength)
            throw RuntimeException("Extending past winLength is undefined")  // TODO: return multiple runs, or extra points?

        val winner = if (newLength == winLength) player else null

        return Run(
            other.start,
            end,
            walker,
            winLength,
            newLength,
            winner,
        )
    }

    fun joinAtEnd(other: Run<CoordinateType, WalkerType, PlayerType>, player: PlayerType): Run<CoordinateType, WalkerType, PlayerType> {
        return this // TODO
    }
}

class RectangularBoardState<TokenType> private constructor(
    val width: Int,
    val height: Int,
    private val matrix: List<List<TokenType?>>,
) {
    constructor(width: Int, height: Int) : this(
        width,
        height,
        List(height) { List<TokenType?>(width) { null } }
    ) {
        if (3 > width || width > 20 || 3 > height || height > 20) {
            throw InvalidBoardSizeException("Board size $width $height is invalid!")
        }
    }

    fun placeTile(tile: TokenType, r: Int, c: Int, player: Int): RectangularBoardState<TokenType> {
        if (0 > r || r >= height || 0 > c || c >= width)
            throw IndexOutOfBoundsException("$r $c is not in bounds!")

        if (matrix[r][c] != null)
            throw AlreadyOccupiedException("$r $c is already occupied!")

        return RectangularBoardState(
            width,
            height,
            matrix.mapIndexed { rowIx, row ->
                if (rowIx != r) row
                else row.mapIndexed { colIx, oldTile -> if (colIx != c) oldTile else tile }
            }
        )
    }

    fun getTile(row: Int, column: Int): TokenType? {
        if (0 > row || row >= height || 0 > column || column >= width)
            throw IndexOutOfBoundsException("$row $column is not in bounds!")

        return matrix[row][column]
    }
}