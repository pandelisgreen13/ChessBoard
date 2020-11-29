package com.pchasapis.chess.common.helper

import com.pchasapis.chess.model.Position
import com.pchasapis.chess.model.Tile
import java.util.*

object BfsHelper {

    // Below arrays details all 8 possible movements for a knight
    private val xMoves = intArrayOf(2, 1, -1, -2, -2, -1, 1, 2)
    private val yMoves = intArrayOf(1, 2, 2, 1, -1, -2, -2, -1)

    fun findPath(start: Tile,
                 end: Tile,
                 chessboard: Array<Array<Tile?>>): ArrayList<Position> {
        val tilePath = mutableListOf<Tile>()
        val knightFinalPath: ArrayList<Position> = arrayListOf()

        var currentPath = chessboard[end.x][end.y]
        while (currentPath?.isEqual(start) == false) {
            tilePath.add(currentPath)
            currentPath = chessboard[currentPath.x][currentPath.y]
        }
        knightFinalPath.add(Position(end.x, end.y))

        tilePath.add(Tile(start.x, start.y, 0))
        tilePath.forEach {
            knightFinalPath.add(Position(it.x, it.y))
        }
        return knightFinalPath
    }

    fun calculateMoves(queue: Queue<Tile>,
                       current: Tile,
                       depth: Int,
                       chessboard: Array<Array<Tile?>>,
                       boardSize: Int): Queue<Tile> {

        // check for all 8 possible movements for a knight
        // and enqueue each valid movement into the queue
        for (i in xMoves.indices) {
            // Get the new valid position of Knight from current
            val x = current.x + xMoves[i]
            val y = current.y + yMoves[i]

            if (inRange(x, y, boardSize) && !isNotVisited(x, y, chessboard)) {
                chessboard[x][y] = Tile(current.x, current.y, depth, true)
                queue.add(Tile(x, y, depth))
            }
        }
        return queue
    }

    //check if node is visited
    private fun isNotVisited(x: Int, y: Int, chessboard: Array<Array<Tile?>>): Boolean {
        val tile = chessboard[x][y]
        return tile?.isVisited ?: false
    }

    // Check if (x, y) is valid chess board coordinates
    private fun inRange(x: Int, y: Int, boardSize: Int): Boolean {
        return x in 0 until boardSize - 1 && 0 <= y && y < boardSize
    }
}