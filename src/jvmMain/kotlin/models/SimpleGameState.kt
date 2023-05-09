package models


data class SimpleGameState private constructor(
    val generalGameState: GeneralGameState,
) : GameState {
    constructor(boardSize: Int, players: List<Player>) : this(GeneralGameState(boardSize, players))

    override fun placeToken(placement: PlayAction.PlaceTile, playerID: PlayerID) =
        copy(generalGameState = generalGameState.placeToken(placement, playerID))

    override fun getModeString() = "Simple"

    override fun getTurn() = generalGameState.getTurn()

    override fun getBoardState() = generalGameState.getBoardState()

    override fun getPlayers() = generalGameState.getPlayers()

    override fun checkGameEnded() = generalGameState.getBoardState().isFull()
            || generalGameState.getBoardState().getSOSs().isNotEmpty() // TODO: Demeter?

    override fun getHistory() = generalGameState.getHistory()

    override fun updatePlayer(id: PlayerID, newPlayer: Player) =
        copy(generalGameState = generalGameState.updatePlayer(id, newPlayer))

    override fun getStatus() =
        if (checkGameEnded())
            generalGameState.getHighScore().let { if (it.size == 1) GameStatus.Won(it[0]) else GameStatus.Tie(it) }
        else
            GameStatus.Ongoing(generalGameState.getPlayers()[generalGameState.getTurn()])
}