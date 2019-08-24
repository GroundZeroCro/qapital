package com.groundzero.qapital.ui

import com.groundzero.qapital.base.BaseViewModelTest
import com.groundzero.qapital.data.persistence.goal.GoalDao
import com.groundzero.qapital.data.remote.goal.Goal
import com.groundzero.qapital.data.remote.goal.GoalRepository
import com.groundzero.qapital.data.remote.goal.Goals
import com.groundzero.qapital.ui.goal.GoalViewModel
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GoalViewModelTest : BaseViewModelTest() {

    @Mock
    lateinit var goalRepository: GoalRepository
    @Mock
    lateinit var goalDao: GoalDao

    private lateinit var goalViewModel: GoalViewModel
    private val goal = Goal("", 0, 0.0f, 0.0f, ",", "", 0, mutableListOf())
    private val goals = Goals(mutableListOf(goal, goal, goal))

    @Before
    fun setUp() {
        goalViewModel = GoalViewModel(goalRepository, goalDao)
    }

    @Test
    fun `selected live data value should be equal to selected goal`() {
        goalViewModel.setSelectedGoalLiveData(goal)
        assertEquals("Should be equal", goal, goalViewModel.getSelectedGoalLiveData().value)
    }

    @Test
    fun `fetched data size should be equal to live data value size`() {
        `when`(goalRepository.getGoals()).thenReturn(Single.just(goals))
        assertEquals("Is equal", goalViewModel.getRemoteGoals().value!!.listData!!.size, 3)
    }

    @Test
    fun `remote fetched data should be stored in local database one time`() {
        `when`(goalRepository.getGoals()).thenReturn(Single.just(goals))
        goalViewModel.getRemoteGoals()
        verify(goalDao, times(1)).addGoals(goals)
    }

    @Test
    fun `on goals remote fetch error cached data should be fetched if cache is not null`() {
        `when`(goalDao.getGoals()).thenReturn(goals)
        goalViewModel.onGoalsFetchError(Throwable())
        verify(goalDao, times(1)).getGoals()
    }
}