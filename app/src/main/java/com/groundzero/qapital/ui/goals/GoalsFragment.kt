package com.groundzero.qapital.ui.goals


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.groundzero.qapital.R
import com.groundzero.qapital.base.BaseFragment
import com.groundzero.qapital.data.goal.Goal
import com.groundzero.qapital.data.response.Status
import kotlinx.android.synthetic.main.fragment_goals.*

class GoalsFragment : BaseFragment(), GoalRecyclerItem {

    private lateinit var adapter: GoalsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_goals, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        goalViewModel.getGoals().observe(viewLifecycleOwner, Observer { response ->
            run {
                when (response.status) {
                    Status.LOADING -> getActivityCallback().changeProgressBarVisibility(true)
                    Status.SUCCESS -> {
                        adapter = GoalsAdapter(context!!, response.listData!!, this)
                        adjustedRecyclerView(goals_recycler_view).adapter = adapter;
                        getActivityCallback().changeProgressBarVisibility(false)
                    }
                    Status.ERROR -> {
                        Toast.makeText(context, resources.getString(R.string.error_fetching_data), Toast.LENGTH_LONG)
                            .show()
                        getActivityCallback().changeProgressBarVisibility(false)
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
        getActivityCallback().changeToolbarTitle("Goals")
    }

    override fun onDestroy() {
        super.onDestroy()
        goalViewModel.onDestroy()
    }
}