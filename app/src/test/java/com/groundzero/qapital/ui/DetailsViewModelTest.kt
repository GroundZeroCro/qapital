package com.groundzero.qapital.ui

import com.groundzero.qapital.base.BaseViewModelTest
import com.groundzero.qapital.data.details.Detail
import com.groundzero.qapital.data.details.Details
import com.groundzero.qapital.data.details.DetailsRepository
import com.groundzero.qapital.ui.details.DetailsViewModel
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
    private lateinit var detailsViewModel: DetailsViewModel

    @Before
    fun setUp() {
        detailsViewModel = DetailsViewModel(detailsRepository)
    }

    @Test
    fun `fetched data size should be equal to live data value size`() {
        val detail = Detail("", "", FEED_TIMESTAMP, "", 0.0f, 0, 2)
        val details = Details(mutableListOf(detail, detail))
        `when`(detailsRepository.getDetails(ArgumentMatchers.anyInt())).thenReturn(
            Single.just(
                details
            )
        )
        assertEquals("Is equal", detailsViewModel.getDetails(1).value!!.listData!!.size, 2)
    }

    @Test
    fun `returns percentage of two floats as true`() {
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

    companion object {
        const val FEED_TIMESTAMP = "2015-03-10T14:55:16.025Z"
    }
}