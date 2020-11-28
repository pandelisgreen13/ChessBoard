package com.pchasapis.chess.mvp.view

import com.pchasapis.chess.model.Position
import java.util.*

interface HomeView {
    fun showPosition(knightPosition: Position, drawableIcon: Int, i: Int)
    fun moviePiece(path: ArrayList<Position>)
    fun showError(errorMessage: Int, count: Int? = null)
    fun removePiece(it: Position)
}