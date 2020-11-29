@file:Suppress("DEPRECATION")

package com.pchasapis.chess.common.utils

import android.content.Context
import android.preference.PreferenceManager
import com.pchasapis.chess.model.Position

class SharePreferencesUtil(private val context: Context) {

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)


    @Synchronized
    fun getKnightPosition(): Position? {
        val x = preferences.getInt(KNIGHT_X, -1)
        val y = preferences.getInt(KNIGHT_Y, -1)

        if (x == -1 || y == -1) {
            return null
        }
        return Position(x, y)
    }

    @Synchronized
    fun getTargetPosition(): Position? {
        val x = preferences.getInt(TARGET_X, -1)
        val y = preferences.getInt(TARGET_Y, -1)

        if (x == -1 || y == -1) {
            return null
        }
        return Position(x, y)
    }

    @Synchronized
    fun getBoardSize(): Int {
        return preferences.getInt(BOARD_SIZE, 6)
    }

    @Synchronized
    fun setKnightPosition(knightX: Int, knightY: Int) {
        preferences.edit().putInt(KNIGHT_X, knightX).apply()
        preferences.edit().putInt(KNIGHT_Y, knightY).apply()
    }

    @Synchronized
    fun setTargetPosition(targetX: Int, targetY: Int) {
        preferences.edit().putInt(TARGET_X, targetX).apply()
        preferences.edit().putInt(TARGET_Y, targetY).apply()
    }

    @Synchronized
    fun setBoardSize(boardSize: Int) {
        preferences.edit().putInt(BOARD_SIZE, boardSize).apply()
    }

    companion object {
        private const val KNIGHT_X = "knightX"
        private const val KNIGHT_Y = "knightY"
        private const val TARGET_X = "targetX"
        private const val TARGET_Y = "targetY"
        private const val BOARD_SIZE = "boardSize"
    }
}