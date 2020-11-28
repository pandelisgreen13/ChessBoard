package com.pchasapis.chess.model

data class Position (var i: Int, var j: Int) {

//    fun equals(pos: Pos): Boolean {
//        return pos.i == this.i && pos.j == this.j
//    }

    override fun toString(): String {
        return "Possition{i= $i, j= $j }"
    }
}