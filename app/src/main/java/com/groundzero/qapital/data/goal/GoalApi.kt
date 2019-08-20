package com.groundzero.qapital.data.goal

import io.reactivex.Single
import retrofit2.http.GET

interface GoalApi {
    @GET("savingsgoals")
    fun getGoals(): Single<Goals>
}