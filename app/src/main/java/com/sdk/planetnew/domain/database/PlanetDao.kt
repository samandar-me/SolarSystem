package com.sdk.planetnew.domain.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sdk.planetnew.data.model.Planet

@Dao
interface PlanetDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun savePlanet(planet: Planet)

    @Query("SELECT * FROM PLANET ORDER BY id DESC")
    fun getAllPlanets(): LiveData<List<Planet>>

    @Delete
    suspend fun deletePlanet(planet: Planet)

    @Query("DELETE FROM Planet")
    suspend fun deleteAllPlanets()
}