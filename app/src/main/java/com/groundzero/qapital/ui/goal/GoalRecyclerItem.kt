package com.groundzero.qapital.ui.goal

import com.groundzero.qapital.data.goal.Goal

interface GoalRecyclerItem {
    fun onGoalClick(goal: Goal)
}