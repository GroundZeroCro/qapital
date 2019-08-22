package com.groundzero.qapital.data.goal

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.gson.annotations.SerializedName
import com.squareup.picasso.Picasso

data class Goals(
    @SerializedName("savingsGoals")
    var savingsGoals: List<Goal>
)

data class Goal(
    @SerializedName("goalImageURL")
    var image: String,
    var userId: Int,
    var targetAmount: Float,
    var currentBalance: Float,
    var status: String,
    var name: String,
    var id: Int,
    var connectedUsers: List<Int>
)

@BindingAdapter("android:src")
fun loadImage(view: ImageView, imageUrl: String) {
    Picasso.get().load(imageUrl)
        .placeholder(com.groundzero.qapital.R.drawable.sand_clock_svg)
        .error(com.groundzero.qapital.R.drawable.error_image_svg)
        .into(view)
}
