package com.pchasapis.chess.mvp.view.home

import com.pchasapis.chess.model.Position
import com.pchasapis.chess.mvp.view.base.BaseView
import java.util.*

interface HomeView : BaseView {
    fun showPosition(knightPosition: Position, drawableIcon: Int, i: Int)
    fun moviePiece(path: ArrayList<Position>)
    fun showError(errorMessage: Int, count: Int? = null)
    fun removePiece(it: Position)
    fun handleLoadingView(visibility: Boolean)
    fun savePositions(knightPosition: Position, targetPosition: Position, boardSize: Int)
}