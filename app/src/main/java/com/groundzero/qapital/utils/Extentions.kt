package com.groundzero.qapital.utils

import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
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
// TODO finish parsing this date into right timestamp for DetailsFragment
fun String.toTimestamp(): String {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
    val calendar = Calendar.getInstance()
    calendar.time = simpleDateFormat.parse(this)
    return calendar.get(Calendar.HOUR).toString()
}