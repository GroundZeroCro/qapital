package com.groundzero.qapital.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.groundzero.qapital.R
import com.groundzero.qapital.base.BaseFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        goalViewModel.getSelectedGoalLiveData().observe(viewLifecycleOwner, Observer { selectedGoal ->
            run {
                getActivityCallback().changeToolbarTitle("${selectedGoal.name} goal")
                details_title.text = selectedGoal.name
                details_amount.text = resources.getString(R.string.details_amount, selectedGoal.targetAmount.toString())

                Picasso.get().load(selectedGoal.image)
                    .placeholder(R.drawable.sand_clock_svg)
                    .error(R.drawable.error_image_svg)
                    .into(details_image)
            }
        })
    }
}
