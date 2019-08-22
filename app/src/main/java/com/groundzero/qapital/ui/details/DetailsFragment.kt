package com.groundzero.qapital.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.groundzero.qapital.R
import com.groundzero.qapital.application.CustomApplication
import com.groundzero.qapital.base.BaseFragment
import com.groundzero.qapital.data.response.Status
import com.groundzero.qapital.ui.goal.GoalViewModel
import com.groundzero.qapital.utils.toCurrency
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : BaseFragment() {

    lateinit var detailsViewModel: DetailsViewModel

    private lateinit var detailsAdapter: DetailsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity!!.application as CustomApplication).getApplicationComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailsViewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailsViewModel::class.java)
        detailsAdapter = DetailsAdapter(context!!, mutableListOf())
        adjustedRecyclerView(details_recycler_adapter).adapter = detailsAdapter

        goalViewModel.getSelectedGoalLiveData().observe(viewLifecycleOwner, Observer { selectedGoal ->
            run {
                activityCallback.changeToolbarTitle("${selectedGoal.name} goal")
                details_title.text = selectedGoal.name
                details_amount.text = resources.getString(R.string.amount, selectedGoal.targetAmount.toCurrency())

                Picasso.get().load(selectedGoal.image)
                    .placeholder(R.drawable.sand_clock_svg)
                    .error(R.drawable.error_image_svg)
                    .into(details_image)
            }

            detailsViewModel.getDetails(selectedGoal.id).observe(viewLifecycleOwner, Observer { response ->
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
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        detailsViewModel.onDestroy()
    }
}
