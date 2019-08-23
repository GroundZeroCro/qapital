package com.groundzero.qapital.ui.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR.goal
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.groundzero.qapital.R
import com.groundzero.qapital.base.BaseFragment
import com.groundzero.qapital.data.goal.Goal
import com.groundzero.qapital.data.response.Status
import com.groundzero.qapital.utils.toCurrency
import kotlinx.android.synthetic.main.fragment_details.*


class DetailsFragment : BaseFragment() {

    private lateinit var detailsViewModel: DetailsViewModel
    private lateinit var detailsAdapter: DetailsAdapter
    private lateinit var selectedGoal: Goal

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater,
            R.layout.fragment_details,
            container,
            false
        )
        selectedGoal = goalViewModel.getSelectedGoalLiveData().value!!
        binding.setVariable(goal, selectedGoal)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailsViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(DetailsViewModel::class.java)
        detailsAdapter = DetailsAdapter(context!!, mutableListOf())
        adjustedRecyclerView(details_recycler_adapter).adapter = detailsAdapter
        activityCallback.changeToolbarTitle("${goalViewModel.getSelectedGoalLiveData().value!!.name} goal")

        detailsViewModel.getDetails(goalViewModel.getSelectedGoalLiveData().value!!.id)
            .observe(viewLifecycleOwner, Observer { response ->
                run {
                    when (response.status) {
                        Status.LOADING -> activityCallback.changeProgressBarVisibility(true)
                        Status.SUCCESS -> {
                            detailsAdapter.updateRecyclerView(response.listData!!)
                            activityCallback.changeProgressBarVisibility(false)
                        }
                        Status.ERROR -> {
                            Toast.makeText(
                                context,
                                resources.getString(R.string.error_fetching_data),
                                Toast.LENGTH_LONG
                            ).show()
                            activityCallback.changeProgressBarVisibility(false)
                        }
                    }
                }
            })

        detailsViewModel.getWeekEarnings()
            .observe(viewLifecycleOwner, Observer { weekEarnings ->
                details_week_earnings.text = weekEarnings.toCurrency()
            })

        detailsViewModel.getTotalEarnings()
            .observe(viewLifecycleOwner, Observer { totalEarnings ->
                details_amount.text = resources.getString(R.string.details_amount,totalEarnings.toCurrency(), selectedGoal.targetAmount.toCurrency())
                progress.progress = detailsViewModel.getTotalEarningsProgression(
                    totalEarnings,
                    selectedGoal.targetAmount
                )
            })
    }
}