package ru.skillbranch.devintensive.extension

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
    getWindow().getDecorView().getWindowVisibleDisplayFrame(rect)
    val screenHeight: Int = getWindowManager().getDefaultDisplay().getHeight()
    val diff: Int = screenHeight - - (rect.bottom - rect.top)
    return diff > 100
}

fun Activity.isKeyboardClosed(): Boolean = !isKeyboardOpen()