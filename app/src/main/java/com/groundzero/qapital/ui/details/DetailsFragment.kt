package com.groundzero.qapital.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.groundzero.qapital.R
import com.groundzero.qapital.base.BaseFragment

class DetailsFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        goalViewModel.getSelectedGoalLiveData().observe(viewLifecycleOwner, Observer { selectedGoal ->
            run {
                getActivityCallback().changeToolbarTitle("${selectedGoal.name} goal")
            }
        })
    }
}
