package com.udacity.asteroidradar.worker

import android.app.Application
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.repository.AsteroidRepo
import retrofit2.HttpException

const val ASTEROID_WORKER = "Asteroid worker"
class AsteroidWorker(appContext: Context, param: WorkerParameters):CoroutineWorker(appContext, param) {


    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = AsteroidRepo(database)

        return try {
            repository.refreshAstroData()
            database.asteroidDao.deletePreviousDayAstero(Constants.getDayDate())
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}