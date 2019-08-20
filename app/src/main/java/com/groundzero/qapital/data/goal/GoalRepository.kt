package com.groundzero.qapital.data.goal

import io.reactivex.Single
import javax.inject.Inject

class GoalRepository @Inject constructor(private val goalApi: GoalApi) {

    fun getGoals(): Single<Goals> = goalApi.getGoals()
}