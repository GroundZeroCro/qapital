package com.groundzero.qapital.ui.goal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.groundzero.qapital.data.goal.Goal
import com.groundzero.qapital.data.goal.GoalRepository
import com.groundzero.qapital.data.response.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoalViewModel @Inject constructor(private val goalRepository: GoalRepository) : ViewModel() {

    private val goalsLiveData = MutableLiveData<Response<Goal>>()
    private val selectedGoalLiveData = MutableLiveData<Goal>()
    private var disposable: Disposable = CompositeDisposable()

    fun getGoals(): LiveData<Response<Goal>> {

        goalsLiveData.value = Response.loading()

        disposable = goalRepository.getGoals()
            .subscribeOn(Schedulers.io())
            .doOnError { e -> goalsLiveData.value = Response.error(e.fillInStackTrace()) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response -> goalsLiveData.value = Response.success(response.savingsGoals) }
        return goalsLiveData
    }

    fun setSelectedGoalLiveData(goal: Goal) {
        selectedGoalLiveData.value = goal
    }

    fun getSelectedGoalLiveData(): LiveData<Goal> = selectedGoalLiveData

    fun onDestroy() {
        disposable.dispose()
    }
}