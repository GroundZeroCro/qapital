package com.groundzero.qapital.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.groundzero.qapital.core.RxSchedulerRule
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
open class BaseViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()
}