package com.groundzero.qapital.data.persistence.details

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.groundzero.qapital.data.remote.details.Detail
import com.groundzero.qapital.data.remote.goal.Goal


object DetailsConverter {

    @TypeConverter
    @JvmStatic
    fun fromString(value: String): List<Detail> {
        val listType = object : TypeToken<List<Detail>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    @JvmStatic
    fun fromArrayList(list: List<Detail>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}
