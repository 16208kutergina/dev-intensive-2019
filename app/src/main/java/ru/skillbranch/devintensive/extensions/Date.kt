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
    val minutes = diffMillis / MINUTE
    val hours = diffMillis / HOUR
    val days = diffMillis / DAY
  answer = when{
      days < -360 -> "более чем через год"
      days > 360 -> "более года назад"
      hours in -(360 * DAY)..-26 ||
      hours in 26..(360 * DAY) -> daysPhrase(days)
      hours in -26..-22 -> "через день"
      hours in 22..26 -> "день назад"
      minutes in -(22*MINUTE)..-75 ||
      minutes in 75..(22*MINUTE) -> hoursPhrase(hours)
      minutes in -75..-45 -> "через час"
      minutes in 45..75 -> "час назад"
      seconds in -(45 * SECOND)..-75 ||
      seconds in 75..(45 * SECOND)-> minutesPhrase(minutes)
      seconds in -75..-45 -> "через минуту"
      seconds in 45..75 -> "минуту назад"
      seconds in -45..-1 -> "через несколько секунд"
      seconds in 1..45 -> "несколько секунд назад"
      seconds in -1..1 -> "только что"
      else -> "время не определено"
  }

    return answer
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