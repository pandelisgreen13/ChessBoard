package com.pchasapis.chess.mvp.presenter.splash

import com.pchasapis.chess.mvp.presenter.base.BasePresenter
import com.pchasapis.chess.mvp.view.splash.SplashView

interface SplashPresenter : BasePresenter<SplashView> {
    fun validateFields(boardSize: String, maxMoves: String)
}