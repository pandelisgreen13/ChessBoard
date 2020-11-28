package com.pchasapis.chess.mvp.presenter

import com.pchasapis.chess.model.Position

interface HomePresenter {
    fun setBoard(boardSize:Int)
    fun calculateTile(piecePosition: Position?, positionTile: Position)
    fun clearChess()
}