package com.groundzero.qapital.data

import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.groundzero.qapital.base.DaoBaseTest
import com.groundzero.qapital.data.persistence.goal.GoalDao
import com.groundzero.qapital.data.remote.goal.Goal
import com.groundzero.qapital.data.remote.goal.Goals
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GoalDaoTest : DaoBaseTest() {

    private lateinit var goalDao: GoalDao
    private val goal =
        Goal("", 0, 0.0f, 0.0f, "", "", 1, mutableListOf())
    private val goals = Goals(mutableListOf(goal))

    @Before
    fun setUp() {
        goalDao = persistenceDatabase.getGoalDao()
    }

    @After
    fun tearDown() {
        persistenceDatabase.close()
    }

    @Test
    fun writeUserAndReadInList() {
        goalDao.addGoals(goals)
        ViewMatchers.assertThat(goalDao.getGoals().savingsGoals[0], CoreMatchers.equalTo(goal))
        assertTrue(goalDao.getGoals().savingsGoals.size == goals.savingsGoals.size)
    }
}