package com.touhidapps.room.utils

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant


@OptIn(ExperimentalTime::class)
fun todayEndOfDayMillis(timeZone: TimeZone = TimeZone.currentSystemDefault()): Long {
    val now = Clock.System.now()
    val today = now.toLocalDateTime(timeZone).date
    val tomorrowStart = today.plus(1, DateTimeUnit.DAY).atStartOfDayIn(timeZone)
    return tomorrowStart.toEpochMilliseconds() - 1
}

@OptIn(ExperimentalTime::class)
fun getCurrentTimeMillis(): Long {
//    val currentMillis: Long = Clock.System.now().toEpochMilliseconds()
//    return currentMillis
    return todayEndOfDayMillis()
}

@OptIn(ExperimentalTime::class)
fun formatMillisDateOnly(millis: Long): String {
    val dateTime = Instant.fromEpochMilliseconds(millis).toLocalDateTime(TimeZone.currentSystemDefault())

    val day = dateTime.day.toString().padStart(2, '0')
    val month = dateTime.month.name.lowercase()
        .replaceFirstChar { it.titlecase() } // "January"
        .take(3) // → "Jan"
    val year = dateTime.year

    return "$day-$month-$year"
}

@OptIn(ExperimentalTime::class)
fun formatMillisWithTime(millis: Long): String {
    val dateTime = Instant.fromEpochMilliseconds(millis)
        .toLocalDateTime(TimeZone.currentSystemDefault())

    val hour = if (dateTime.hour % 12 == 0) 12 else dateTime.hour % 12
    val minute = dateTime.minute.toString().padStart(2, '0')
    val amPm = if (dateTime.hour < 12) "AM" else "PM"

    val day = dateTime.day.toString().padStart(2, '0')
    val month = dateTime.month.name.lowercase()
        .replaceFirstChar { it.titlecase() } // "January"
        .take(3) // → "Jan"
    val year = dateTime.year

    return "$hour:$minute $amPm $day-$month-$year"
}
