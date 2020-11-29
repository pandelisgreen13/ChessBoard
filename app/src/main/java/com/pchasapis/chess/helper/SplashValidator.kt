package com.pchasapis.chess.helper

object SplashValidator {

    fun validateMaxMoves(maxMoves: String): Boolean {
        return maxMoves.isNotEmpty() && maxMoves.toInt() > 0
    }

    fun validateBoardSize(boardSize: String): Boolean {
        return boardSize.isNotEmpty() && boardSize.toInt() in 3..16
    }
}