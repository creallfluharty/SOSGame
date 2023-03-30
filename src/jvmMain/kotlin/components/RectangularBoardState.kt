package components

class AlreadyOccupiedException(message: String) : RuntimeException(message)

class InvalidBoardSizeException(message: String) : RuntimeException(message)

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