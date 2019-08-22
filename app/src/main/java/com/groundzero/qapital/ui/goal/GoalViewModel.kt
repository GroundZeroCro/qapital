package com.groundzero.qapital.ui.goal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.groundzero.qapital.data.goal.Goal
import com.groundzero.qapital.data.goal.GoalRepository
import com.groundzero.qapital.data.response.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Singleton
class GoalViewModel(private val goalRepository: GoalRepository) : ViewModel() {

    private val goals = MutableLiveData<Response<Goal>>()
    private val selectedGoal = MutableLiveData<Goal>()
    private lateinit var disposable: Disposable

    fun getGoals(): LiveData<Response<Goal>> {

        goals.value = Response.loading()

        disposable = goalRepository.getGoals()
            .subscribeOn(Schedulers.io())
            .doOnError { e -> goals.value = Response.error(e.fillInStackTrace()) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response -> goals.value = Response.success(response.savingsGoals) }
        return goals
    }

    fun setSelectedGoalLiveData(goal: Goal) {
        selectedGoal.value = goal
    }

    fun getSelectedGoalLiveData(): LiveData<Goal> = selectedGoal


    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}