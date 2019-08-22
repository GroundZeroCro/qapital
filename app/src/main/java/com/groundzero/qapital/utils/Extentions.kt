package com.groundzero.qapital.utils

import android.os.Build
import android.text.Html
import android.text.Spanned
import java.text.SimpleDateFormat
import java.util.*

private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)

// Time format received in response
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

fun String.secondsPassed(): Int {
    // Seconds passed since now and post date
    return ((System.currentTimeMillis() - simpleDateFormat.parse(this).time) / 1000).toInt()
}

fun String.toTimestamp(): String {
    val yearsSince = secondsPassed() / secondsInYear
    val daysSince = secondsPassed() % secondsInYear / secondsInDay
    val hoursSince = secondsPassed() % secondsInYear % secondsInDay / secondsInHour
    val minutesSince = secondsPassed() % secondsInYear % secondsInDay % secondsInHour / secondsInMinute
    val seconds = secondsPassed() % secondsInYear % secondsInDay % secondsInHour % secondsInMinute
    // Removing 0 values from the timestamp
    var timestamp = ""
    if (yearsSince != 0) timestamp += "$yearsSince" + "y "
    if (daysSince != 0) timestamp += "$daysSince" + "d "
    if (hoursSince != 0) timestamp += "$hoursSince" + "h "
    if (minutesSince != 0) timestamp += "$minutesSince" + "m "
    timestamp += "$seconds" + "s"
    return timestamp
}