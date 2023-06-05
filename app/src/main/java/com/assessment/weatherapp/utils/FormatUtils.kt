package com.assessment.weatherapp.utils

import android.os.Build
import com.assessment.weatherapp.R
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.text.SimpleDateFormat
import java.util.*


fun getFormattedDate(date: Int): String {
    val dateTime = getDateTimeFromEpoch(date)
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        formatDateParts(getDateFromDateTime(dateTime))
    } else {
        TODO("VERSION.SDK_INT < O")
    }
}

fun getWeekDayName(date: String): String {
    val format = SimpleDateFormat("yyyy-M-d", Locale.ENGLISH)
    val calendar = Calendar.getInstance()
    calendar.time = format.parse(date) ?: Date()
    val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
    return SimpleDateFormat("EEEE", Locale.ENGLISH).format(calendar.time)
}

fun mapMonthNumberToName(monthNumber: String): String {
    return when (monthNumber) {
        "01" -> "January"
        "02" -> "February"
        "03" -> "March"
        "04" -> "April"
        "05" -> "May"
        "06" -> "June"
        "07" -> "July"
        "08" -> "August"
        "09" -> "September"
        "10" -> "October"
        "11" -> "November"
        "12" -> "December"
        else -> "NA"
    }
}


fun formatDateParts(date: String): String {
    val datePartsArray = date.split('-')
    val formattedDateNumber = datePartsArray[2]
    val formattedMonth = mapMonthNumberToName(datePartsArray[1])
    val formattedDay = getWeekDayName(date)
    return "${formattedDay}, $formattedDateNumber $formattedMonth"
}

fun getDateFromDateTime(dateTime: String): String {
    var i = 0
    var date = ""
    while (dateTime[i] != 'T') {
        date += dateTime[i]
        i++
    }
    return date
}

fun getDateTimeFromEpoch(date: Int): String {
    return Instant
        .fromEpochSeconds(date.toLong())
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .toString()
}


fun getImageFromUrl(url: String): Int {
    return when (url) {
        "01d" -> R.drawable.ic_sun
        "01n" -> R.drawable.ic_moon
        "02d" -> R.drawable.ic_few_clouds
        "02n" -> R.drawable.ic_night_clouds
        "03d", "03n" -> R.drawable.ic_scattered_clouds
        "04d", "04n" -> R.drawable.ic_broken_clouds
        "09d", "09n" -> R.drawable.ic_shower_rain
        "10d", "10n" -> R.drawable.ic_rain
        "11d", "11n" -> R.drawable.ic_thunderstorm
        "13d", "13n" -> R.drawable.ic_snow
        "50d", "50n" -> R.drawable.ic_mist
        else -> R.drawable.ic_launcher_foreground
    }
}
