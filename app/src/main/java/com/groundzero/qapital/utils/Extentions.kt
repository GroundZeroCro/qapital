package com.groundzero.qapital.utils

import android.os.Build
import android.text.Html
import android.text.Spanned
import java.text.SimpleDateFormat
import java.util.*

fun String.toSpanned(): Spanned {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION")
        return Html.fromHtml(this)
    }
}

fun Float.toCurrency(): String {
    return String.format("%.2f", this)
}

fun String.toTimestamp(): String {
    // Time format received in response
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
    // Seconds passed since now and post date
    val time: Int = ((System.currentTimeMillis() - simpleDateFormat.parse(this).time) / 1000).toInt()

    val secondsInYear = 365 * 24 * 60 * 60
    val secondsInDay = 24 * 60 * 60
    val secondsInHour = 60 * 60
    val secondsInMinute = 60

    val yearsSince = time / secondsInYear
    val daysSince = time % secondsInYear / secondsInDay
    val hoursSince = time % secondsInYear % secondsInDay / secondsInHour
    val minutesSince = time % secondsInYear % secondsInDay % secondsInHour / secondsInMinute
    val seconds = time % secondsInYear % secondsInDay % secondsInHour % secondsInMinute
    // Removing 0 values from the timestamp
    var timestamp = ""
    if (yearsSince != 0) timestamp += "$yearsSince" + "y "
    if (daysSince != 0) timestamp += "$daysSince" + "d "
    if (hoursSince != 0) timestamp += "$hoursSince" + "h "
    if (minutesSince != 0) timestamp += "$minutesSince" + "m "
    timestamp += "$seconds" + "s"
    return timestamp
}