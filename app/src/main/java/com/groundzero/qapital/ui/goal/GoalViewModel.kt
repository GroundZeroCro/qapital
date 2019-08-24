package com.groundzero.qapital.ui.goal

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.groundzero.qapital.data.persistence.goal.GoalDao
import com.groundzero.qapital.data.remote.goal.Goal
import com.groundzero.qapital.data.remote.goal.GoalRepository
import com.groundzero.qapital.data.remote.goal.Goals
import com.groundzero.qapital.data.response.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Singleton
class GoalViewModel(
    private val goalRepository: GoalRepository,
    private val goalDao: GoalDao
) : ViewModel() {

    private val goals = MutableLiveData<Response<Goal>>()
    private val selectedGoal = MutableLiveData<Goal>()
    private lateinit var disposable: Disposable

    fun getRemoteGoals(): LiveData<Response<Goal>> {

        goals.value = Response.loading()

        disposable = goalRepository.getGoals()
            .subscribeOn(Schedulers.io())
            .doOnError { e -> Log.e("errors", e.message) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    goals.value = Response.success(response.savingsGoals)
                    cacheGoals(Goals(response.savingsGoals))
                },
                { throwable: Throwable? ->
                    val cachedGoals: Goals? = getCachedGoals()
                    if (cachedGoals != null) {
                        goals.value = Response.success(cachedGoals.savingsGoals)
                    } else {
                        goals.value = Response.error(throwable!!)
                    }
                }
            )
        return goals
    }

    private fun getCachedGoals(): Goals {
        return goalDao.getGoals()
    }

    private fun cacheGoals(goals: Goals) {
        goalDao.addGoals(goals)
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