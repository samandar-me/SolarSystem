package com.sdk.planetnew.domain.favorite

import com.sdk.planetnew.data.model.Planet
import com.sdk.planetnew.domain.database.PlanetDatabase

class FavoriteRepository(private val db: PlanetDatabase) {
    fun getAllPlanets() = db.planetDao().getAllPlanets()

    suspend fun deleteAllPlanets() = db.planetDao().deleteAllPlanets()

    suspend fun deleteAllNumbers() = db.numberDao().deleteAllNumbers()
}