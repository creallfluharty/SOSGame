package models

import components.Token

sealed class PlayAction {
    data class PlaceTile(val r: Int, val c: Int, val what: Token) : PlayAction()
}