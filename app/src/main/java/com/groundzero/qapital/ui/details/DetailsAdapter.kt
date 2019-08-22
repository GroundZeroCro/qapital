package com.groundzero.qapital.ui.details

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.groundzero.qapital.R
import com.groundzero.qapital.data.details.Detail
import com.groundzero.qapital.utils.toCurrency
import com.groundzero.qapital.utils.toSpanned
import com.groundzero.qapital.utils.toTimestamp

class DetailsAdapter(
    private val context: Context,
    private var details: List<Detail>
) :
    RecyclerView.Adapter<DetailsAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder =
        CustomViewHolder(LayoutInflater.from(context), parent, context)

    fun updateRecyclerView(details: List<Detail>) {
        this.details = details
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = details.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(details[position])
    }

    class CustomViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        private val context: Context
    ) :
        RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.item_detail, parent, false)) {

        private val detailDescription: TextView = itemView.findViewById(R.id.details_item_description)
        private val detailTime: TextView = itemView.findViewById(R.id.details_item_time)
        private val detailAmount: TextView = itemView.findViewById(R.id.details_item_amount)

        fun bind(detail: Detail) {
            detailDescription.text = detail.message.toSpanned()
            detailTime.text = detail.timestamp.toTimestamp()
            detailAmount.text = context.resources.getString(R.string.amount, detail.amount.toCurrency())
        }
    }
}