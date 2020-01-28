package ru.skillbranch.devintensive.extensions



fun String.truncate(count: Int = 16): String {
    val trim = this.trim()
    if (trim.length < count) return "$trim..."
    val substring = trim.substring(0, count).trim()
    return "$substring..."
}

fun String.stripHtml() : String{
    return "ok"
    //return Jsoup.parse( this ).text()
}