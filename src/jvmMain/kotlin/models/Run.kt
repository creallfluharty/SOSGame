package models

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