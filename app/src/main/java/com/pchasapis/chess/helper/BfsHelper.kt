package com.pchasapis.chess.helper

import android.util.Log
import com.pchasapis.chess.model.Position
import com.pchasapis.chess.model.Tile
import com.pchasapis.chess.ui.activity.HomeActivity.Companion.BOARD_DEFAULT_DIMENSION
import java.util.*

object BfsHelper {

    private val xMoves = intArrayOf(2, 1, -1, -2, -2, -1, 1, 2)
    private val yMoves = intArrayOf(1, 2, 2, 1, -1, -2, -2, -1)

    fun findPath(start: Tile,
                 end: Tile,
                 currentTile: Tile,
                 chessboard: Array<Array<Tile?>>): ArrayList<Position> {
        val tilePath = mutableListOf<Tile>()
        val knightFinalPath: ArrayList<Position> = arrayListOf()

        var currentPath = chessboard[end.x][end.y]
        while (currentPath?.isEqual(start) == false) {
            tilePath.add(currentPath)
            currentPath = chessboard[currentPath.x][currentPath.y]
        }
        tilePath.add(Tile(start.x, start.y, 0))

        knightFinalPath.add(Position(currentTile.x, currentTile.y))
        tilePath.forEach {
            knightFinalPath.add(Position(it.x, it.y))
        }
        return knightFinalPath
    }

    fun calculateMoves(queue: Queue<Tile>,
                       current: Tile,
                       depth: Int,
                       chessboard: Array<Array<Tile?>>){
        for (i in xMoves.indices) {
            val x = current.x + xMoves[i]
            val y = current.y + yMoves[i]

            if (inRange(x, y) && !isNotVisited(x, y, chessboard)) {
                chessboard[x][y] = Tile(current.x, current.y, depth, true)
                queue.add(Tile(x, y, depth))
            }
        }
        Log.d("queue-> ", "${queue.size}")
    }

    private fun isNotVisited(x: Int, y: Int, chessboard: Array<Array<Tile?>>): Boolean {
        val tile = chessboard[x][y]
        return tile?.isVisited ?: false
    }


    private fun inRange(x: Int, y: Int): Boolean {
        return x in 0 until BOARD_DEFAULT_DIMENSION - 1 && 0 <= y && y < BOARD_DEFAULT_DIMENSION
    }
}