package com.pchasapis.chess.common.extensions

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.view.inputmethod.InputMethodManager

@SuppressLint("WrongConstant")
fun closeSoftKeyboard(activity: Activity) {
    try {
        val inputManager = activity.getSystemService("input_method") as InputMethodManager
        inputManager.hideSoftInputFromWindow(activity.currentFocus?.windowToken, 2)
    } catch (ex: Exception) {
        Log.d("Expection", ex.toString())
    }
}