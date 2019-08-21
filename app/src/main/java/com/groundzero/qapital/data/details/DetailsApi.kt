package com.groundzero.qapital.data.details

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailsApi {
    @GET("/savingsgoals/{id}/feed")
    fun getDetails(
        @Path("id") goalId: Int
    ): Single<Details>
}