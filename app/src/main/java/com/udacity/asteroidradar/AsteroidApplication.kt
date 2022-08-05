package com.udacity.asteroidradar

import android.app.Application
import android.os.Build
import androidx.work.*
import com.udacity.asteroidradar.work.RefreshDataWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AsteroidApplication:Application() {

    private val applicationCoroutine= CoroutineScope(Dispatchers.Default)
    override fun onCreate() {
        super.onCreate()
        refresh()
    }

    private fun refresh() {
        applicationCoroutine.launch {
            setUpWork()
        }
    }

    private fun setUpWork() {

        val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.UNMETERED)
        .setRequiresBatteryNotLow(true)
        .setRequiresCharging(true)
        .apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setRequiresDeviceIdle(true)
            }
        }.build()


        val repeatingRequest= PeriodicWorkRequestBuilder<RefreshDataWorker>(
            1,
            TimeUnit.DAYS
        ).setConstraints(constraints).build()


        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            RefreshDataWorker.WORK_NAME,ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }
}