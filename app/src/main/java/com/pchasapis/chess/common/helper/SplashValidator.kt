package com.pchasapis.chess.common.helper

object SplashValidator {

    fun validateMaxMoves(maxMoves: String): Boolean {
        return maxMoves.isNotEmpty() && maxMoves.toInt() > 0
    }

    fun validateBoardSize(boardSize: String): Boolean {
        return boardSize.isNotEmpty() && boardSize.toInt() in MIN_BOARD_SIZE..MAX_BOARD_SIZE
    }

    private const val MIN_BOARD_SIZE = 6
    private const val MAX_BOARD_SIZE = 16
}