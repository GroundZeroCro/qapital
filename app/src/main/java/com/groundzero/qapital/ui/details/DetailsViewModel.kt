package com.groundzero.qapital.ui.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.groundzero.qapital.data.cache.Cache
import com.groundzero.qapital.data.persistence.details.DetailsDao
import com.groundzero.qapital.data.remote.details.Detail
import com.groundzero.qapital.data.remote.details.Details
import com.groundzero.qapital.data.remote.details.DetailsRepository
import com.groundzero.qapital.data.response.Response
import com.groundzero.qapital.utils.NetworkUtils
import com.groundzero.qapital.utils.secondsInDay
import com.groundzero.qapital.utils.secondsPassed
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DetailsViewModel(
    private val detailsRepository: DetailsRepository,
    private val detailsDao: DetailsDao,
    private val networkUtils: NetworkUtils
) : ViewModel(), Cache<Details> {

    private var disposable = CompositeDisposable()
    private val details = MutableLiveData<Response<Detail>>()
    private val weekEarnings = MutableLiveData<Float>()
    private val totalEarnings = MutableLiveData<Float>()

    fun getDetails(goalId: Int): LiveData<Response<Detail>> {
        details.value = Response.loading()
        val detailsObserver: Single<Details> = detailsRepository.getDetails(goalId)

        if (networkUtils.isNetworkConnected()) {
            disposable.add(getDetailsFeed(goalId, detailsObserver))
        } else {
            setCachedLiveData(goalId, Throwable())
        }

        return details
    }

    private fun getDetailsFeed(goalId: Int, detailsObserver: Single<Details>): Disposable {
        return detailsObserver
            .subscribeOn(Schedulers.io())
            .doOnError { e -> Log.e("errors", e.message) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response -> setRemoteLiveDataAndCacheData(goalId, response) },
                { throwable -> setCachedLiveData(goalId, throwable) }
            )
    }

    fun setRemoteLiveDataAndCacheData(goalId: Int, response: Details) {
        details.value = Response.success(response.details)
        cacheData(response.apply { id = goalId })
    }

    fun setCachedLiveData(goalId: Int, throwable: Throwable?) {
        val cachedGoals: Details? = getCachedData(goalId)
        if (cachedGoals != null) details.value = Response.success(cachedGoals.details)
        else details.value = Response.error(throwable!!)
    }

    override fun getCachedData(id: Int?): Details? {
        return detailsDao.getDetails(id!!)
    }

    override fun cacheData(t: Details) {
        detailsDao.addDetail(t)
    }

    fun setWeeklyEarningsLiveData(details: List<Detail>) {
        this.weekEarnings.value =
            details.filter { detail: Detail -> detail.timestamp.secondsPassed() < secondsInDay * 7 }
                .map { detail -> detail.amount }
                .sum()
    }

    // I have used another thread to make the text and progression bar animate
    fun setTotalEarningsLiveData(details: List<Detail>) {
        val total: Float = details.map { detail -> detail.amount }.sum()
        Thread {
            var earningsIterator = 0.0f
            for (x in 0..total.toInt()) {
                Thread.sleep(15)
                // Setting rounded integers
                this.totalEarnings.postValue(earningsIterator++)
            }
            // Setting final float
            this.totalEarnings.postValue(total)
        }.start()
    }

    fun getWeekEarnings(): LiveData<Float> {
        return weekEarnings
    }

    fun getTotalEarnings(): LiveData<Float> {
        return totalEarnings
    }

    fun getTotalEarningsProgression(totalEarnings: Float, targetEarnings: Float): Int {
        return ((totalEarnings / targetEarnings) * 100).toInt()
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}