package com.groundzero.qapital.ui

import com.groundzero.qapital.base.BaseViewModelTest
import com.groundzero.qapital.data.persistence.details.DetailsDao
import com.groundzero.qapital.data.remote.details.Detail
import com.groundzero.qapital.data.remote.details.Details
import com.groundzero.qapital.data.remote.details.DetailsRepository
import com.groundzero.qapital.ui.details.DetailsViewModel
import com.groundzero.qapital.utils.ExtensionsTest
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailsViewModelTest : BaseViewModelTest() {

    @Mock
    lateinit var detailsRepository: DetailsRepository
    @Mock
    lateinit var detailsDao: DetailsDao
    private lateinit var detailsViewModel: DetailsViewModel
    private val detail = Detail(
        "", "", "", "", 0.0f, 0, 0
    )

    @Before
    fun setUp() {
        detailsViewModel = DetailsViewModel(detailsRepository, detailsDao)
    }

    @Test
    fun `fetched data size should be equal to live data value size`() {
        // Getting date formatted from more then a week old date
        detail.timestamp = ExtensionsTest.getDateFormatted(10000000)
        val details = Details(1, mutableListOf(detail, detail))
        `when`(detailsRepository.getDetails(ArgumentMatchers.anyInt())).thenReturn(
            Single.just(
                details
            )
        )
        detailsViewModel.getDetails(1)
        assertEquals("Is equal", 2, detailsViewModel.getDetails(1).value!!.listData!!.size)
        detail.timestamp = ""
    }

    @Test
    fun `fetch past week earnings should be equal`() {
        detail.timestamp = ExtensionsTest.getDateFormatted(59)
        detail.amount = 2.0f
        val details = Details(1, mutableListOf(detail, detail))
        detailsViewModel.setWeeklyEarningsLiveData(details.details)
        assertEquals(
            "Is same value",
            4.0f,
            detailsViewModel.getWeekEarnings().value
        )
        detail.timestamp = ""
        detail.amount = 0.0f
    }

    @Test
    fun `returns percentage of two floats as equal`() {
        assertEquals(
            "Is equal",
            detailsViewModel.getTotalEarningsProgression(30.0f, 100.0f),
            30
        )
    }

    @Test
    fun `returns percentage of two floats as false`() {
        assertFalse(
            detailsViewModel.getTotalEarningsProgression(30.0f, 100.0f) == 31
        )
    }
}