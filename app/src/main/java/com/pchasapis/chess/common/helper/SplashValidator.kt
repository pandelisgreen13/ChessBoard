package com.pchasapis.chess.common.helper

object SplashValidator {

    fun validateMaxMoves(maxMoves: String): Boolean {
        return maxMoves.isNotEmpty() && maxMoves.toInt() > 0
    }

    fun validateBoardSize(boardSize: String,
                          minBoardSize: Int = 0,
                          maxBoardSize: Int = 0): Boolean {
        if (minBoardSize > maxBoardSize) {
            return false
        }
        return boardSize.isNotEmpty() && boardSize.toInt() in minBoardSize..maxBoardSize
    }
}