package com.groundzero.qapital.data.details

import com.google.gson.annotations.SerializedName

data class Details(
    @SerializedName("feed")
    var details: List<Detail>
)

data class Detail(
    var id: String,
    var type: String,
    var timestamp: String,
    var message: String,
    var amount: Float,
    var userId: Int,
    var savingRulesId: Int
)