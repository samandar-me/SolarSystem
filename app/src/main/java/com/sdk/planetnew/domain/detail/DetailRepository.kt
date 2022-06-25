package com.sdk.planetnew.domain.detail

import com.sdk.planetnew.data.model.Number
import com.sdk.planetnew.data.model.Planet
import com.sdk.planetnew.domain.database.PlanetDatabase

class DetailRepository(private val db: PlanetDatabase) {

    suspend fun savePlanet(planet: Planet) = db.planetDao().savePlanet(planet)
    suspend fun saveNumber(num: Number) = db.numberDao().saveNumber(num)

    fun getAllNumbers() = db.numberDao().getAllNumbers()
}