package com.groundzero.qapital.utils

import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class ExtensionsTest {

    @Test
    fun `timestamp should equal true`() {
        val differenceFromNowInSeconds = secondsInMinute - 1
        val requiredTime = System.currentTimeMillis() - differenceFromNowInSeconds * 1000 // An hour before now
        val simpleDate = SimpleDateFormat(responseDateFormat, Locale.ENGLISH)
        assertEquals(simpleDate.format(requiredTime).toTimestamp(), "$differenceFromNowInSeconds" + "s")
    }

    @Test
    fun `should return string with a currency sign`() {
        val amount = 2.0f
        assertEquals("Is equal", amount.toCurrency(), "$2,00")
    }
}