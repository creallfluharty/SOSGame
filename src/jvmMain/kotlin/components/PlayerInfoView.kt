package components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import models.GameStatus
import models.Player

@Composable
fun PlayerInfoView(gameStatus: GameStatus, players: List<Player>, height: Dp) {
    Row {
        for (player in players) {
            Text("Player ${player.getID()}: ${player.getScore()}")
            Spacer(Modifier.height(height).width(height))
        }

        when (gameStatus) {
            is GameStatus.Ongoing -> {
                Text("Player ${gameStatus.turn.getID()}'s turn.")
            }
            is GameStatus.Won -> {
                Text("Player ${gameStatus.winner.getID()} won!")
            }
            is GameStatus.Tie -> {
                Text("Players ${gameStatus.players.map { it.getID() }} tied!")
            }
        }
    }
}