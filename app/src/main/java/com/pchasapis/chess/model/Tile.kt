package com.pchasapis.chess.model

data class Tile(val x: Int,
                val y: Int,
                var depth: Int = Integer.MAX_VALUE,
                var isVisited: Boolean = false) {

    fun isEqual(other: Tile): Boolean {
        return this.x == other.x && this.y == other.y
    }
}