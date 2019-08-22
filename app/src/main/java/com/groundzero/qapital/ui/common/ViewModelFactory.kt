package com.groundzero.qapital.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.groundzero.qapital.data.details.DetailsRepository
import com.groundzero.qapital.data.goal.GoalRepository
import com.groundzero.qapital.ui.details.DetailsViewModel
import com.groundzero.qapital.ui.goal.GoalViewModel
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ViewModelFactory @Inject constructor(
    private val goalRepository: GoalRepository,
    private val detailsRepository: DetailsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(GoalViewModel::class.java) -> GoalViewModel(this.goalRepository) as T
            modelClass.isAssignableFrom(DetailsViewModel::class.java) -> DetailsViewModel(this.detailsRepository) as T
            else -> throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}