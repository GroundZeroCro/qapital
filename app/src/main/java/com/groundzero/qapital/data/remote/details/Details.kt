package com.groundzero.qapital.data.remote.details

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "details")
data class Details(
    @PrimaryKey
    var id: Int,
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