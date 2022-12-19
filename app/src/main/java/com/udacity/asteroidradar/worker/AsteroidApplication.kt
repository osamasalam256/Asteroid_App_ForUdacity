package com.udacity.asteroidradar.worker

import android.app.Application
import androidx.work.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@Suppress("DEPRECATION")
class AsteroidApplication: Application() {


    override fun onCreate() {
        super.onCreate()
        delayedInit()
    }

    private val applicationScope = CoroutineScope(Dispatchers.Default)
    private fun delayedInit() = applicationScope.launch {
        setupRecurringWork()
    }

    private fun setupRecurringWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .apply {
                    setRequiresDeviceIdle(true)
            }.build()

        val repeatingRequest = PeriodicWorkRequestBuilder<AsteroidWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            ASTEROID_WORKER,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest)
    }
}