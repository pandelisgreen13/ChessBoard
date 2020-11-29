package com.pchasapis.chess.mvp.presenter.home

import com.pchasapis.chess.model.Position

interface HomePresenter {
    fun setBoard()
    fun calculateTile(positionTile: Position)
    fun clearChess()
    fun getBoardSize():Int
}