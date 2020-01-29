package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.graphics.Rect
import android.view.inputmethod.InputMethodManager


fun Activity.hideKeyboard(){
    val imm: InputMethodManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
}

fun Activity.isKeyboardOpen(): Boolean{
    val rect = Rect()
    window.decorView.getWindowVisibleDisplayFrame(rect)
    val display = this.resources.displayMetrics
    val diff: Int = display.heightPixels - (rect.bottom - rect.top)
    return diff > 100
}

fun Activity.isKeyboardClosed(): Boolean = !isKeyboardOpen()