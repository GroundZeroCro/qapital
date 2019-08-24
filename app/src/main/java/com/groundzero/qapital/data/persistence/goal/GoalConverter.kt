package com.groundzero.qapital.data.persistence.goal

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.groundzero.qapital.data.remote.goal.Goal


object GoalConverter {

    @TypeConverter
    @JvmStatic
    fun fromString(value: String): List<Goal> {
        val listType = object : TypeToken<List<Goal>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    @JvmStatic
    fun fromArrayList(list: List<Goal>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}
