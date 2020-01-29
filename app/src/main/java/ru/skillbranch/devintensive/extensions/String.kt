package ru.skillbranch.devintensive.extensions


fun String.truncate(count: Int = 16): String {
    val trim = this.trim()
    if (trim.length < count) return "$trim..."
    val substring = trim.substring(0, count).trim()
    return "$substring..."
}

fun String.stripHtml() : String{
    val text = this.trim()
    val patternSpace = ("\\s+").toRegex()
    val patternTag = ("<[^>]*>").toRegex()
    val patternOut = ("[&'\"]").toRegex()
    return text.replace(patternSpace," ").replace(patternTag, "").replace(patternOut, "")
}