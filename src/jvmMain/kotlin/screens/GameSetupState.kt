package screens

import components.NumberInputFieldState
import components.RadioSelectionState

data class GameSetupState(
    val gameModeSelection: RadioSelectionState<GameMode>,
    val playModeSelection: RadioSelectionState<PlayMode>,
    val boardSizeInput: NumberInputFieldState,
    val recordGame: Boolean,
) : ScreenState {
    companion object {
        val PLAY_MODES = linkedMapOf(PlayMode.PassAndPlay to "Pass-and-Play", PlayMode.Singleplayer to "Singleplayer (vs AI)")
        val GAME_MODES = linkedMapOf(GameMode.Simple to "Simple", GameMode.General to "General")

        val DEFAULT_GAME_MODE = GameMode.Simple
        val DEFAULT_PLAY_MODE = PlayMode.PassAndPlay
        const val DEFAULT_BOARD_SIZE = 8
        const val MIN_BOARD_SIZE = 3
        const val MAX_BOARD_SIZE = 20
        const val DEFAULT_RECORD_GAME = false
    }

    constructor() : this(
        RadioSelectionState(GAME_MODES, DEFAULT_GAME_MODE),
        RadioSelectionState(PLAY_MODES, DEFAULT_PLAY_MODE),
        NumberInputFieldState(DEFAULT_BOARD_SIZE, MIN_BOARD_SIZE, MAX_BOARD_SIZE),
        DEFAULT_RECORD_GAME
    )

    fun selectGameMode(gameMode: GameMode) = copy(gameModeSelection = gameModeSelection.makeSelection(gameMode))
    fun getSelectedGameMode() = gameModeSelection.getSelected()

    fun selectPlayMode(playMode: PlayMode) = copy(playModeSelection = playModeSelection.makeSelection(playMode))
    fun getSelectedPlayMode() = playModeSelection.getSelected()

    fun replaceBoardSizeInput(input: String) = copy(boardSizeInput = boardSizeInput.replaceInput(input))
    fun checkBoardSizeIsValid() = boardSizeInput.isValid()

    fun getBoardSize() = boardSizeInput.getValidValue()
    fun setRecordGame(recordGame: Boolean) = copy(recordGame = recordGame)
}