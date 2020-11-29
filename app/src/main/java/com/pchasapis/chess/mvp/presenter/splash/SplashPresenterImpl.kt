package com.pchasapis.chess.mvp.presenter.splash

import com.pchasapis.chess.R
import com.pchasapis.chess.common.helper.SplashValidator
import com.pchasapis.chess.mvp.presenter.base.BasePresenterImpl
import com.pchasapis.chess.mvp.view.splash.SplashView

class SplashPresenterImpl(view: SplashView) : BasePresenterImpl<SplashView>(view), SplashPresenter {

    override fun validateFields(boardSize: String, maxMoves: String) {
        if (!isViewAttached()) {
            return
        }

        if (!SplashValidator.validateMaxMoves(maxMoves)) {
            getView()?.showError(R.string.splash_moves_error)
            return
        }

        if (!SplashValidator.validateBoardSize(boardSize, MIN_BOARD_SIZE, MAX_BOARD_SIZE)) {
            getView()?.showError(R.string.splash_board_error)
            return
        }

        getView()?.proceedToHome(maxMoves, boardSize)
    }

    companion object {
        private const val MIN_BOARD_SIZE = 6
        private const val MAX_BOARD_SIZE = 16
    }
}