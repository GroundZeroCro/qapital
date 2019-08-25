package com.groundzero.qapital.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.groundzero.qapital.base.DaoBaseTest
import com.groundzero.qapital.data.persistence.PersistenceDatabase
import com.groundzero.qapital.data.persistence.details.DetailsDao
import com.groundzero.qapital.data.remote.details.Detail
import com.groundzero.qapital.data.remote.details.Details
import com.groundzero.qapital.utils.responseDateFormat
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.text.SimpleDateFormat
import java.util.*

@RunWith(AndroidJUnit4::class)
class DetailsDaoTest: DaoBaseTest() {

    private lateinit var detailsDao: DetailsDao
    private val detail =
        Detail("", "", getDateFormatted(99), "", 0.0f, 0, 0)
    private val details = Details(0, mutableListOf(detail))

    @Before
    fun setUp() {
        detailsDao = persistenceDatabase.getDetailsDao()
    }

    @After
    fun tearDown() {
        persistenceDatabase.close()
    }

    @Test
    fun writeUserAndReadInList() {
        detailsDao.addDetail(details)
        ViewMatchers.assertThat(detailsDao.getDetails(0).details[0], CoreMatchers.equalTo(detail))
    }

    companion object {
        fun getDateFormatted(pastDifferenceFromNowInSeconds: Int): String {
            val requiredTime =
                System.currentTimeMillis() - pastDifferenceFromNowInSeconds * 1000 // An hour before now
            val simpleDate = SimpleDateFormat(responseDateFormat, Locale.ENGLISH)
            return simpleDate.format(requiredTime)
        }
    }
}