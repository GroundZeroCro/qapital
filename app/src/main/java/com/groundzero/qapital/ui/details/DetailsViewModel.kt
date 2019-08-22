package com.groundzero.qapital.ui.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.groundzero.qapital.data.details.Detail
import com.groundzero.qapital.data.details.Details
import com.groundzero.qapital.data.details.DetailsRepository
import com.groundzero.qapital.data.response.Response
import com.groundzero.qapital.utils.secondsInDay
import com.groundzero.qapital.utils.secondsPassed
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DetailsViewModel(private val detailsRepository: DetailsRepository) : ViewModel() {

    private var disposable = CompositeDisposable()
    private val details = MutableLiveData<Response<Detail>>()
    private val weekEarnings = MutableLiveData<Float>()

    fun getDetails(goalId: Int): LiveData<Response<Detail>> {

        details.value = Response.loading()
        val detailsObserver: Single<Details> = detailsRepository.getDetails(goalId)

        disposable.add(
            detailsObserver
                .subscribeOn(Schedulers.io())
                .doOnError { e -> details.value = Response.error(e.fillInStackTrace()) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response -> details.value = Response.success(response.details) }
        )

        disposable.add(
            detailsObserver
                .map { details: Details ->
                    details.details
                        .filter { detail: Detail -> detail.timestamp.secondsPassed() < secondsInDay * 7 }
                        .map { detail -> detail.amount }
                        .sum()
                }
                .subscribeOn(Schedulers.io())
                .doOnError { e -> details.value = Response.error(e.fillInStackTrace()) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { weekEarnings -> this.weekEarnings.value = weekEarnings }
        )

        return details
    }

    fun getWeekEarnings(): LiveData<Float> {
        return weekEarnings
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}