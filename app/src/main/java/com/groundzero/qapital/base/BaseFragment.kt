package com.groundzero.qapital.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.groundzero.qapital.application.CustomApplication
import com.groundzero.qapital.ui.goal.GoalViewModel
import javax.inject.Inject

open class BaseFragment : Fragment() {

    @Inject
    lateinit var goalViewModel: GoalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity!!.application as CustomApplication).getApplicationComponent().inject(this)
    }

    fun adjustedRecyclerView(recyclerView: RecyclerView): RecyclerView {
        recyclerView.setHasFixedSize(true)
        val linearLayout = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayout
        return recyclerView
    }

    fun getActivityCallback(): MainActivityCallback {
        return (activity as MainActivityCallback)
    }
}