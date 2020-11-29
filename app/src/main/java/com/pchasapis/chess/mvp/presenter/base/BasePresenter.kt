package com.pchasapis.chess.mvp.presenter.base

import com.pchasapis.chess.mvp.view.base.BaseView

interface BasePresenter<out V : BaseView> {

    fun detach()
    fun getView(): V?
    fun isViewAttached(): Boolean
}