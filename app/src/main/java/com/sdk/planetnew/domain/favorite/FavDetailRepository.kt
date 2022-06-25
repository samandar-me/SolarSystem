package com.sdk.planetnew.domain.favorite

import com.sdk.planetnew.data.model.Number
import com.sdk.planetnew.data.model.Planet
import com.sdk.planetnew.domain.database.PlanetDatabase

class FavDetailRepository(
    private val db: PlanetDatabase
) {
    suspend fun deleteFromDatabase(planet: Planet) = db.planetDao().deletePlanet(planet)

    suspend fun deleteNumber(num: Number) = db.numberDao().deleteNumber(num)
}