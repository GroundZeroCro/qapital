package com.groundzero.qapital.ui.goal


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.groundzero.qapital.R
import com.groundzero.qapital.base.BaseFragment
import com.groundzero.qapital.data.remote.goal.Goal
import com.groundzero.qapital.data.response.Status
import kotlinx.android.synthetic.main.fragment_goals.*

class GoalsFragment : BaseFragment(), GoalRecyclerItem {

    private lateinit var goalsAdapter: GoalsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_goals, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goalsAdapter = GoalsAdapter(context!!, mutableListOf(), this)
        adjustedRecyclerView(goals_recycler_view).adapter = goalsAdapter

        goals_refresh.setOnClickListener { goalViewModel.getRemoteGoals() }

        goalViewModel.getRemoteGoals().observe(viewLifecycleOwner, Observer { response ->
            run {
                when (response.status) {
                    Status.LOADING -> {
                        activityCallback.changeProgressBarVisibility(true)
                        goals_refresh.visibility = View.GONE
                    }
                    Status.SUCCESS -> {
                        goalsAdapter.updateRecyclerView(response.listData!!)
                        activityCallback.changeProgressBarVisibility(false)
                        goals_refresh.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        Toast.makeText(
                            context,
                            resources.getString(R.string.error_fetching_data),
                            Toast.LENGTH_LONG
                        ).show()
                        activityCallback.changeProgressBarVisibility(false)
                        goals_refresh.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    override fun onGoalClick(goal: Goal) {
        goalViewModel.setSelectedGoalLiveData(goal)
        Navigation.findNavController(view!!).navigate(R.id.detailsFragment)
    }

    override fun onStart() {
        super.onStart()
        activityCallback.changeToolbarTitle("Goals")
    }
}