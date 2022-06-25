package com.sdk.planetnew.domain.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sdk.planetnew.data.model.Number
import com.sdk.planetnew.data.model.Planet
import com.sdk.planetnew.data.util.Constants.DB_NAME

@Database(entities = [Planet::class,Number::class], version = 5, exportSchema = false)
abstract class PlanetDatabase : RoomDatabase() {

    abstract fun planetDao(): PlanetDao
    abstract fun numberDao(): NumberDao

    companion object {
        @Volatile
        private var instance: PlanetDatabase? = null

        operator fun invoke(context: Context) = instance ?: synchronized(Any()) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context,
            PlanetDatabase::class.java,
            DB_NAME
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }
}