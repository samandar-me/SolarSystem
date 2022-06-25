package com.sdk.planetnew.domain.database

import androidx.room.*
import com.sdk.planetnew.data.model.Number

@Dao
interface NumberDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveNumber(num: Number)

    @Query("SELECT * FROM Number ORDER BY id DESC")
    fun getAllNumbers(): List<Number>

    @Delete
    suspend fun deleteNumber(num: Number)

    @Query("DELETE FROM Number")
    suspend fun deleteAllNumbers()
}