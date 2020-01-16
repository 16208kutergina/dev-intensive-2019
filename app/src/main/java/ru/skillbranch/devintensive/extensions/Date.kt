package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR
const val MONTH = 30 * DAY
const val YEAR = 12 * MONTH

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {
    val diffMillis = diffDateInMillis(date)
    var answer = "только что"
    val seconds = diffMillis / SECOND
    if (seconds != 0L) {
        answer = secondsPhrase(seconds)
    }
    val minutes = diffMillis / MINUTE
    if (minutes != 0L) {
        answer = minutesPhrase(minutes)
    }
    val hours = diffMillis / HOUR
    if (hours != 0L) {
        answer = hoursPhrase(hours)
    }
    val days = diffMillis / DAY
    if (days != 0L) {
        answer = daysPhrase(days)
    }
//    val month = diffMillis / MONTH
//    if (month != 0L) {
//        answer = monthPhrase(month)
//    }
    val years = diffMillis / YEAR
    if (years != 0L) {
        answer = yearPhrase(years)
    }

    return answer
}

fun secondsPhrase(seconds: Long): String {
    val dozens = seconds % 100
    val units = seconds % 10
    return when {
        dozens in 5..19 -> "$seconds секунд назад"
        dozens in -19..-5 -> "через ${abs(seconds)} секунд"
        units == 1L -> "${abs(seconds)} секунду назад"
        units in 2..4 -> "$seconds секунды назад"
        units == -1L -> "через ${abs(seconds)} секунду"
        units in -4..-2 -> "через ${abs(seconds)} секунды"
        seconds < 0 -> "через ${abs(seconds)} секунд"
        else -> "${abs(seconds)} секунд назад"
    }
}

fun minutesPhrase(minutes: Long): String {
    val dozens = minutes % 100
    val units = minutes % 10
    return when {
        dozens in 5..19 -> "$minutes минут назад"
        dozens in -19..-5 -> "через ${abs(minutes)} минут"
        units == 1L -> "${abs(minutes)} минуту назад"
        units in 2..4 -> "$minutes минуты назад"
        units == -1L -> "через ${abs(minutes)} минуту"
        units in -4..-2 -> "через ${abs(minutes)} минуты"
        minutes < 0 -> "через ${abs(minutes)} минут"
        else -> "${abs(minutes)} минут назад"
    }
}

fun hoursPhrase(hours: Long): String {
    val dozens = hours % 100
    val units = hours % 10
    return when {
        dozens in 5..19 -> "$hours часов назад"
        dozens in -19..-5 -> "через ${abs(hours)} часов"
        units == 1L -> "${abs(hours)} час назад"
        units in 2..4 -> "$hours часа назад"
        units == -1L -> "через ${abs(hours)} час"
        units in -4..-2 -> "через ${abs(hours)} часа"
        hours < 0 -> "через ${abs(hours)} часов"
        else -> "${abs(hours)} часов назад"
    }
}

fun daysPhrase(days: Long): String {
    val dozens = days % 100
    val units = days % 10
    return when {
        dozens in 5..19 -> "$days дней назад"
        dozens in -19..-5 -> "через ${abs(days)} дней"
        units == 1L -> "${abs(days)} день назад"
        units in 2..4 -> "$days дня назад"
        units == -1L -> "через ${abs(days)} день"
        units in -4..-2 -> "через ${abs(days)} дня"
        days < 0 -> "через ${abs(days)} дней"
        else -> "${abs(days)} дней назад"
    }
}

fun monthPhrase(month: Long): String {
    val dozens = month % 100
    val units = month % 10
    return when {
        dozens in 5..19 -> "$month месяцев назад"
        dozens in -19..-5 -> "через ${abs(month)} месяцев"
        units == 1L -> "${abs(month)} месяц назад"
        units in 2..4 -> "$month месяца назад"
        units == -1L -> "через ${abs(month)} месяц"
        units in -4..-2 -> "через ${abs(month)} месяца"
        month < 0 -> "через ${abs(month)} месяцев"
        else -> "${abs(month)} месяцев назад"
    }
}

private fun yearPhrase(years: Long): String {
    return when {
        years >= 1L -> "более года назад"
        years <= -1L -> "более чем через год"
        else -> "период неизвестен"
    }
}

private fun Date.diffDateInMillis(date: Date): Long {
    val now = date.time
    val before = this.time
    return now - before
}

enum class TimeUnits {
    SECOND {
        override fun plural(number: Long): String {
            val dozens = abs(number) % 100
            val units = abs(number) % 10
            return when {
                abs(dozens) in 5..19 -> "$number секунд"
                abs(units) == 1L -> "${number} секунду"
                abs(units) in 1..4 -> "$number секунды"
                else -> "$number секунд"
            }
        }
    },
    MINUTE{
        override fun plural(number: Long): String{
            val dozens = abs(number) % 100
            val units = abs(number) % 10
            return when {
                abs(dozens) in 5..19 -> "$number минут"
                abs(units) == 1L -> "${number} минуту"
                abs(units) in 2..4 -> "$number минуты"
                else -> "$number минут"
            }
        }
    },
    HOUR{
        override fun plural(number: Long): String{
            val dozens = abs(number) % 100
            val units = abs(number) % 10
            return when {
                abs(dozens) in 5..19 -> "$number часов"
                abs(units) == 1L -> "${number} час"
                abs(units) in 2..4 -> "$number часа"
                else -> "$number часов"
            }
        }
    },
    DAY{
        override fun plural(number: Long): String{
            val dozens = abs(number) % 100
            val units = abs(number) % 10
            return when {
                abs(dozens) in 5..19 -> "$number дней"
                abs(units) == 1L -> "${number} день"
                abs(units) in 2..4 -> "$number дня"
                else -> "$number дней"
            }
        }
    };

    abstract fun plural(number: Long): String
}