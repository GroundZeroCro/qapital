package com.groundzero.qapital.data.persistence.details

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.groundzero.qapital.data.remote.details.Details

@Dao
interface DetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDetail(details: Details)

    @Query("SELECT * FROM details WHERE id = :id")
    fun getDetails(id: Int): Details
}