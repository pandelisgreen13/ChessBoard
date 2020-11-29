package com.pchasapis.chess.mvp.view.splash

import androidx.annotation.StringRes
import com.pchasapis.chess.mvp.view.base.BaseView

interface SplashView : BaseView {
    fun proceedToHome(maxMoves: String, boardSize: String)
    fun showError(@StringRes splashBoardError: Int)
}