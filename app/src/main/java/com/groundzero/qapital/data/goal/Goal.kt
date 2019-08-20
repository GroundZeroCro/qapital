package com.groundzero.qapital.data.goal

import com.google.gson.annotations.SerializedName

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