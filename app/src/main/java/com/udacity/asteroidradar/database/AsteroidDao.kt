package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AsteroidDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(asteroidList: List<AsteroidEntity>)
    @Query("SELECT * FROM AsteroidEntity ORDER BY closeApproachDate")
    fun getAllAsteroids(): Flow<List<AsteroidEntity>>

    @Query("SELECT * FROM AsteroidEntity WHERE closeApproachDate >= :startDate AND closeApproachDate <= :endDate ORDER BY closeApproachDate")
    fun getAsteroidWithSpecificDate(startDate: String, endDate: String): Flow<List<AsteroidEntity>>

    @Query("DELETE FROM AsteroidEntity WHERE closeApproachDate < :today")
    suspend fun deletePreviousDayAstero(today: String)


}

@Database(entities = [AsteroidEntity::class], version = 1, exportSchema = false)
abstract class AsteroidDatabase: RoomDatabase() {

    abstract val asteroidDao: AsteroidDao
}
private  lateinit var INSTANCE: AsteroidDatabase

fun getDatabase(context: Context): AsteroidDatabase {
             synchronized(AsteroidDatabase::class.java) {
                 if (!::INSTANCE.isInitialized){
                     INSTANCE= Room.databaseBuilder(
                         context,
                         AsteroidDatabase::class.java,
                         "AsteroidDatabase")
                         .build()
                 }
            }
    return INSTANCE
}


