package com.groundzero.qapital.data.persistence.goal

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.groundzero.qapital.data.remote.goal.Goals

@Dao
interface GoalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addGoals(goals: Goals)

    @Query("SELECT * FROM goals")
    fun getGoals(): Goals
}