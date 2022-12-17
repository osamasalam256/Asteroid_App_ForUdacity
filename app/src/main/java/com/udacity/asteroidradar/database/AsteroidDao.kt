package com.udacity.asteroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
@Dao
interface AsteroidDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(asteroidList: List<AsteroidEntity>)
    @Query("SELECT * FROM AsteroidEntity ORDER BY closeApproachDate")
    fun getAllAsteroids(): LiveData<List<AsteroidEntity>>

    @Query("SELECT * FROM AsteroidEntity WHERE closeApproachDate>= :startDate AND closeApproachDate<=:endDate ORDER BY closeApproachDate")
    fun getAsteroidWithSpecificDate(startDate: String, endDate: String): LiveData<List<AsteroidEntity>>


}

@Database(entities = [AsteroidEntity::class], version = 1, exportSchema = false)
abstract class AsteroidDatabase: RoomDatabase(){

abstract val asteroidDao: AsteroidDao

    companion object {

        @Volatile
        private var INSTANCE: AsteroidDatabase? = null
        fun getDatabase(context: Context): AsteroidDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AsteroidDatabase::class.java,
                    "Asteroid_database")
                    .build()
                INSTANCE = instance

                instance
            }
        }

    }
}