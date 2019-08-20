package com.groundzero.qapital.ui.goals

import com.groundzero.qapital.data.goal.Goal

interface GoalRecyclerItem {
    fun onGoalClick(goal: Goal)
}