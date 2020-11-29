package com.pchasapis.chess.mvp.presenter.base

import com.pchasapis.chess.mvp.view.base.BaseView
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import java.lang.ref.WeakReference

open class BasePresenterImpl<out V : BaseView>(view: V) : BasePresenter<V> {

    private var viewRef: WeakReference<V>? = null

    protected var job = SupervisorJob()
    protected val uiDispatcher: CoroutineDispatcher = Dispatchers.Main
    protected val bgDispatcher: CoroutineDispatcher = Dispatchers.IO
    protected val uiScope = CoroutineScope(Dispatchers.Main + job)

    init {
        viewRef = WeakReference(view)
    }

    override fun getView(): V? {
        return viewRef?.get()
    }

    override fun isViewAttached(): Boolean {
        return viewRef != null && viewRef!!.get() != null && viewRef!!.get()!!.isAttached()
    }

    override fun detach() {
        uiScope.coroutineContext.cancelChildren()
        viewRef?.clear()
    }
}