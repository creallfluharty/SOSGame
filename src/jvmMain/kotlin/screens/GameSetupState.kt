package screens

import components.NumberInputFieldState
import components.RadioSelectionState
import components.ToggleCarouselState
import models.ScreenState

data class GameSetupState(
    val gameModeSelection: RadioSelectionState<GameMode>,
    val player1TypeToggle: ToggleCarouselState<PlayerType>,
    val player2TypeToggle: ToggleCarouselState<PlayerType>, // TODO: List<PlayerType>
    val boardSizeInput: NumberInputFieldState,
    val recordGame: Boolean,
) : ScreenState {
    companion object {
        val GAME_MODES = linkedMapOf(GameMode.Simple to "Simple", GameMode.General to "General")
        val PLAYER_TYPES = listOf(Pair("humanPlayer.png", PlayerType.Human), Pair("computerPlayer.png", PlayerType.Computer))

        val DEFAULT_GAME_MODE = GameMode.Simple
        const val DEFAULT_BOARD_SIZE = 8
        const val MIN_BOARD_SIZE = 3
        const val MAX_BOARD_SIZE = 20
        const val DEFAULT_RECORD_GAME = false
    }

    constructor() : this(
        RadioSelectionState(GAME_MODES, DEFAULT_GAME_MODE),
        ToggleCarouselState(PLAYER_TYPES),
        ToggleCarouselState(PLAYER_TYPES),
        NumberInputFieldState(DEFAULT_BOARD_SIZE, MIN_BOARD_SIZE, MAX_BOARD_SIZE),
        DEFAULT_RECORD_GAME,
    )

    fun selectGameMode(gameMode: GameMode) = copy(gameModeSelection = gameModeSelection.makeSelection(gameMode))
    fun getSelectedGameMode() = gameModeSelection.getSelected()

    fun togglePlayer1Type() = copy(player1TypeToggle = player1TypeToggle.advance())
    fun togglePlayer2Type() = copy(player2TypeToggle = player2TypeToggle.advance())

    fun replaceBoardSizeInput(input: String) = copy(boardSizeInput = boardSizeInput.replaceInput(input))
    fun checkBoardSizeIsValid() = boardSizeInput.isValid()

    fun getBoardSize() = boardSizeInput.getValidValue()
    fun setRecordGame(recordGame: Boolean) = copy(recordGame = recordGame)
}