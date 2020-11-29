@file:Suppress("DEPRECATION")

package com.pchasapis.chess.common.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.pchasapis.chess.model.Position


object SharePreferencesUtil {

    @Synchronized
    private fun getSharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Synchronized
    fun getKnightPosition(context: Context): Position? {
        val preferences = getSharedPreferences(context)
        val x = preferences.getInt(KNIGHT_X, -1)
        val y = preferences.getInt(KNIGHT_Y, -1)

        if (x == -1 || y == -1) {
            return null
        }
        return Position(x, y)
    }

    @Synchronized
    fun getTargetPosition(context: Context): Position? {
        val preferences = getSharedPreferences(context)
        val x = preferences.getInt(TARGET_X, -1)
        val y = preferences.getInt(TARGET_Y, -1)

        if (x == -1 || y == -1) {
            return null
        }
        return Position(x, y)
    }

    @Synchronized
    fun getBoardSize(context: Context): Int {
        val preferences = getSharedPreferences(context)
        return preferences.getInt(BOARD_SIZE, 6)
    }

    @Synchronized
    fun getMaxMoves(context: Context): Int {
        val preferences = getSharedPreferences(context)
        return preferences.getInt(MAX_MOVES, -1)
    }

    @Synchronized
    fun setKnightPosition(knightX: Int, knightY: Int, context: Context) {
        val preferences = getSharedPreferences(context)
        preferences.edit().putInt(KNIGHT_X, knightX).apply()
        preferences.edit().putInt(KNIGHT_Y, knightY).apply()
    }

    @Synchronized
    fun setTargetPosition(targetX: Int, targetY: Int, context: Context) {
        val preferences = getSharedPreferences(context)
        preferences.edit().putInt(TARGET_X, targetX).apply()
        preferences.edit().putInt(TARGET_Y, targetY).apply()
    }

    @Synchronized
    fun setBoardSize(boardSize: Int, context: Context) {
        val preferences = getSharedPreferences(context)
        preferences.edit().putInt(BOARD_SIZE, boardSize).apply()
    }

    @Synchronized
    fun setMaxMoves(maxMoves: Int, context: Context) {
        val preferences = getSharedPreferences(context)
        preferences.edit().putInt(MAX_MOVES, maxMoves).apply()
    }

    private const val KNIGHT_X = "knightX"
    private const val KNIGHT_Y = "knightY"
    private const val TARGET_X = "targetX"
    private const val TARGET_Y = "targetY"
    private const val BOARD_SIZE = "boardSize"
    private const val MAX_MOVES = "moves"
}