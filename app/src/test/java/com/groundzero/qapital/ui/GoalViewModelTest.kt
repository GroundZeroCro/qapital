package com.groundzero.qapital.ui

import com.groundzero.qapital.core.BaseTest
import com.groundzero.qapital.data.goal.Goal
import com.groundzero.qapital.data.goal.GoalRepository
import com.groundzero.qapital.data.goal.Goals
import com.groundzero.qapital.ui.goal.GoalViewModel
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`


class GoalViewModelTest : BaseTest() {

    @Mock
    lateinit var goalRepository: GoalRepository


    private lateinit var goalViewModel: GoalViewModel

    @Before
    fun setUp() {
        goalViewModel = GoalViewModel(goalRepository)
    }

    @Test
    fun `selected live data value should be equal to selected goal`() {
        val goal = Goal("", 0, 0.0f, 0.0f, ",", "", 0, mutableListOf())
        goalViewModel.setSelectedGoalLiveData(goal)
        assertEquals("Should be equal", goal, goalViewModel.getSelectedGoalLiveData().value)
    }

    @Test
    fun `fetched data should be equal to live data value`() {
        val goal = Goal("", 0, 0.0f, 0.0f, ",", "", 0, mutableListOf())
        val goals = Goals(mutableListOf(goal, goal, goal))
        `when`(goalRepository.getGoals()).thenReturn(Single.just(goals))
        assertEquals("Is equal", goalViewModel.getGoals().value!!.listData!!.size, 3)
    }
}