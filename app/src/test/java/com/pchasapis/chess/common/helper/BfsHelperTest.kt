package com.pchasapis.chess.common.helper

import com.pchasapis.chess.model.Position
import com.pchasapis.chess.model.Tile
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Test
import java.util.*

class BfsHelperTest {


    @Test
    fun `Given start and end position with one move When BfsHelper is called Then it returns knight actual path`() {
        val result =
            BfsHelper.findPath(startPosition(5, 2), endPosition(3, 3), chessBoardWithOneMove())

        val expectedList: ArrayList<Position> = arrayListOf()
        expectedList.add(Position(3, 3))
        expectedList.add(Position(5, 2))

        assertNotNull(result)
        expectedList.forEachIndexed { index, _ ->
            assertEquals(expectedList[index].i, result[index].i)
        }
    }

    @Test
    fun `Given start and end position with three moves When BfsHelper is called Then it returns knight actual path`() {
        val result =
            BfsHelper.findPath(startPosition(5, 2), endPosition(0, 4), chessBoardWithThreeMoves())

        val expectedList: ArrayList<Position> = arrayListOf()
        expectedList.add(Position(0, 4))
        expectedList.add(Position(2, 5))
        expectedList.add(Position(4, 4))
        expectedList.add(Position(5, 2))

        assertNotNull(result)
        expectedList.forEachIndexed { index, _ ->
            assertEquals(expectedList[index].i, result[index].i)
        }
    }

    private fun startPosition(x: Int, y: Int): Tile {
        return createTile(x, y)
    }

    private fun endPosition(x: Int, y: Int): Tile {
        return createTile(x, y)
    }

    private fun createTile(x: Int, y: Int): Tile {
        return Tile(x, y)
    }

    private fun chessBoardWithOneMove(): Array<Array<Tile?>> {
        val chessBoard = Array(6) { arrayOfNulls<Tile>(6) }
        chessBoard[3][2] = createTile(5, 2)
        return chessBoard
    }

    private fun chessBoardWithThreeMoves(): Array<Array<Tile?>> {
        val chessBoard = Array(6) { arrayOfNulls<Tile>(6) }
        chessBoard[0][4] = createTile(2, 5)
        chessBoard[2][5] = createTile(4, 4)
        chessBoard[4][4] = createTile(5, 2)
        return chessBoard
    }
}