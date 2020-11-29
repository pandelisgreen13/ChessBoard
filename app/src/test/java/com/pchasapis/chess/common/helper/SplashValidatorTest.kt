package com.pchasapis.chess.common.helper

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class SplashValidatorTest {

    @Test
    fun `Given empty moves When SplashValidator is called Then it returns false`() {
        val result = SplashValidator.validateMaxMoves(moves(empty()))
        assertFalse(result)
    }

    @Test
    fun `Given zero moves When SplashValidator is called Then it returns false`() {
        val result = SplashValidator.validateMaxMoves(moves("0"))
        assertFalse(result)
    }

    @Test
    fun `Given moves greater of zero When SplashValidator is called Then it returns true`() {
        val result = SplashValidator.validateMaxMoves(moves("1"))
        assertTrue(result)
    }

    @Test
    fun `Given board size empty without boardSize When SplashValidator is called Then it returns false`() {
        val result = SplashValidator.validateBoardSize(boardSize(empty()))
        assertFalse(result)
    }

    @Test
    fun `Given minBoardSize is greater than maxBoardSize When SplashValidator is called Then it returns false`() {
        val result = SplashValidator.validateBoardSize(
            boardSize(empty()),
            minBoardSize(),
            maxBoardSize(5)
        )
        assertFalse(result)
    }

    @Test
    fun `Given boardSize leaser of minBoardSize and minBoardSize is equals with maxBoardSize When SplashValidator is called Then it returns false`() {
        val result = SplashValidator.validateBoardSize(
            boardSize("1"),
            minBoardSize(),
            maxBoardSize(6)
        )
        assertFalse(result)
    }

    @Test
    fun `Given boardSize greater of minBoardSize and minBoardSize is equals with maxBoardSize When SplashValidator is called Then it returns false`() {
        val result = SplashValidator.validateBoardSize(
            boardSize("7"),
            minBoardSize(),
            maxBoardSize(10)
        )
        assertTrue(result)
    }
    @Test
    fun `Given boardSize greater of maxBoardSize and minBoardSize is equals with maxBoardSize When SplashValidator is called Then it returns false`() {
        val result = SplashValidator.validateBoardSize(
            boardSize("11"),
            minBoardSize(),
            maxBoardSize(10)
        )
        assertFalse(result)
    }

    private fun boardSize(boardSize: String): String {
        return boardSize
    }

    private fun moves(numberOfMoves: String): String {
        return numberOfMoves
    }

    private fun empty(): String {
        return ""
    }

    private fun minBoardSize(): Int {
        return 6
    }

    private fun maxBoardSize(value: Int): Int {
        return value
    }
}