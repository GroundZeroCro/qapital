package com.groundzero.qapital.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.groundzero.qapital.data.details.Detail
import com.groundzero.qapital.data.details.DetailsRepository
import com.groundzero.qapital.data.response.Response
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailsViewModel @Inject constructor(private val detailsRepository: DetailsRepository) : ViewModel() {

    private lateinit var disposable: Disposable
    private val details = MutableLiveData<Response<Detail>>()

    init {
        Logger.i("DetailsViewModel has been created!")
    }

    fun getDetails(goalId: Int): LiveData<Response<Detail>> {

        details.value = Response.loading()

        disposable = detailsRepository.getDetails(goalId)
            .subscribeOn(Schedulers.io())
            .doOnError { e -> details.value = Response.error(e.fillInStackTrace()) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response -> details.value = Response.success(response.details) }
        return details
    }

    fun onDestroy() {
        disposable.dispose()
    }
}