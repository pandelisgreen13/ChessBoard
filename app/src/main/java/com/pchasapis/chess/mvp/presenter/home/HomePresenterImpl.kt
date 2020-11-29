package com.pchasapis.chess.mvp.presenter.home

import android.util.Log
import com.pchasapis.chess.R
import com.pchasapis.chess.helper.BfsHelper
import com.pchasapis.chess.model.Position
import com.pchasapis.chess.model.Tile
import com.pchasapis.chess.mvp.presenter.base.BasePresenterImpl
import com.pchasapis.chess.mvp.view.home.HomeView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class HomePresenterImpl(private val homeView: HomeView,
                        private val maxMoves: Int,
                        private val boardSize: Int)
    : BasePresenterImpl<HomeView>(homeView), HomePresenter {

    private var knightPosition: Position? = null
    private var targetPosition: Position? = null
    private var queue: Queue<Tile> = LinkedList()
    private var isNotReachable: Boolean = true
    private val scope = CoroutineScope(Job() + Dispatchers.Main)

    private var chessboard =
        Array(boardSize) { arrayOfNulls<Tile>(boardSize) }


    override fun getBoardSize(): Int {
        return boardSize
    }

    override fun setBoard() {
        if (!isViewAttached()) {
            return
        }
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                chessboard[i][j] = Tile(
                    Integer.MAX_VALUE,
                    Integer.MAX_VALUE,
                    Integer.MAX_VALUE)
            }
        }
    }

    override fun calculateTile(piecePosition: Position?, positionTile: Position) {
        if (!isViewAttached()) {
            return
        }
        if (knightPosition == null) {
            knightPosition = Position(positionTile.i, positionTile.j)
            homeView.showPosition(knightPosition!!, R.drawable.ic_knight_black, 100)
            return
        }

        if (targetPosition == null && knightPosition?.equals(positionTile) == false) {
            targetPosition = Position(positionTile.i, positionTile.j)
            homeView.showPosition(targetPosition!!, R.drawable.ic_flag, 200)

            val startTile = Tile(knightPosition!!.i, knightPosition!!.j, 0, true)
            val endTile = Tile(targetPosition!!.i, targetPosition!!.j)

            chessboard[knightPosition!!.i][knightPosition!!.j] = startTile

            queue.add(startTile)
            var currentTile: Tile
            scope.launch {
                while (queue.size != 0) {
                    currentTile = queue.poll()

                    if (endTile.isEqual(currentTile)) {
                        isNotReachable = false
                        Log.d("Steps->", currentTile.depth.toString())
                        if (currentTile.depth > maxMoves) {
                            homeView.showError(R.string.error_steps, maxMoves)
                            return@launch
                        }
                        val knightPath = withContext(Dispatchers.Default) {
                            BfsHelper.findPath(
                                startTile,
                                endTile,
                                currentTile,
                                chessboard)
                        }
                        if (!isViewAttached()) {
                            return@launch
                        }
                        homeView.moviePiece(knightPath)
                    } else {
                        withContext(Dispatchers.Default) {
                            BfsHelper.calculateMoves(
                                queue,
                                currentTile,
                                ++currentTile.depth,
                                chessboard,
                                boardSize)
                        }
                    }
                }
                if (isNotReachable) {
                    if(!isViewAttached()){
                        return@launch
                    }
                    homeView.showError(R.string.error_not_reached)
                }
            }
        }
    }

    override fun clearChess() {
        if(!isViewAttached()){
            return
        }
        knightPosition?.let {
            homeView.removePiece(it)
            knightPosition = null
        }

        targetPosition?.let {
            homeView.removePiece(it)
            chessboard = Array(boardSize) { arrayOfNulls(boardSize) }
            setBoard()
            queue = LinkedList()
            isNotReachable = true
            targetPosition = null
        }
    }
}