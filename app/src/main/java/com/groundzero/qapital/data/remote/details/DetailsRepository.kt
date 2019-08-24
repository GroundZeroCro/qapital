package com.groundzero.qapital.data.remote.details

import io.reactivex.Single
import javax.inject.Inject

class DetailsRepository @Inject constructor(private val detailsApi: DetailsApi) {

    fun getDetails(goalId: Int): Single<Details> = detailsApi.getDetails(goalId)
}