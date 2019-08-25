package com.groundzero.qapital.data.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.groundzero.qapital.data.persistence.details.DetailsConverter
import com.groundzero.qapital.data.persistence.details.DetailsDao
import com.groundzero.qapital.data.persistence.goal.GoalConverter
import com.groundzero.qapital.data.persistence.goal.GoalDao
import com.groundzero.qapital.data.remote.details.Details
import com.groundzero.qapital.data.remote.goal.Goals

@TypeConverters(GoalConverter::class, DetailsConverter::class)
@Database(entities = [Goals::class, Details::class], version = 1, exportSchema = false)
abstract class PersistenceDatabase : RoomDatabase() {

    abstract fun getGoalDao(): GoalDao
    abstract fun getDetailsDao(): DetailsDao
}