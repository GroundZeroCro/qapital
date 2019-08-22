package com.groundzero.qapital.ui.goal

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.groundzero.qapital.R
import com.groundzero.qapital.data.goal.Goal
import com.groundzero.qapital.utils.toCurrency
import com.squareup.picasso.Picasso

class GoalsAdapter(
    private val context: Context,
    private var goals: List<Goal>,
    private val goalRecyclerItem: GoalRecyclerItem
) :
    RecyclerView.Adapter<GoalsAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder =
        CustomViewHolder(LayoutInflater.from(context), parent, goalRecyclerItem, context)

    fun updateRecyclerView(goals: List<Goal>) {
        this.goals = goals
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = goals.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(goals[position])
    }

    class CustomViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        private val goalRecyclerItem: GoalRecyclerItem,
        private val context: Context
    ) :
        RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.item_goal, parent, false)) {

        private var goalImage: ImageView = itemView.findViewById(R.id.goal_image)
        private var goalCosts: TextView = itemView.findViewById(R.id.goal_costs)
        private var goalTitle: TextView = itemView.findViewById(R.id.goal_title)

        fun bind(goal: Goal) {

            Picasso.get().load(goal.image)
                .placeholder(R.drawable.sand_clock_svg)
                .error(R.drawable.error_image_svg)
                .into(goalImage)

            goalCosts.text = context.resources.getString(R.string.amount, goal.targetAmount.toCurrency())
            goalTitle.text = goal.name

            itemView.setOnClickListener { goalRecyclerItem.onGoalClick(goal) }
        }
    }
}