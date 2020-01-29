package ru.skillbranch.devintensive.extensions

import org.jsoup.Jsoup


fun String.truncate(count: Int = 16): String {
    val trim = this.trim()
    if (trim.length < count) return "$trim..."
    val substring = trim.substring(0, count).trim()
    return "$substring..."
}

fun String.stripHtml() : String{
    return Jsoup.parse( this ).text()
}