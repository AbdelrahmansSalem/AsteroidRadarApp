package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.Database.getDatabase
import com.udacity.asteroidradar.repository.RepositoryAsteroid
import retrofit2.HttpException

class RefreshDataWorker(context: Context,parameters: WorkerParameters):
    CoroutineWorker(context,parameters) {
    override suspend fun doWork(): Result {
        var database= getDatabase(applicationContext)
        var repository=RepositoryAsteroid(database)
        try {
            repository.refreshData()
            repository.deleteYesterday()
            return Result.success()
        }
        catch (exception:HttpException){
            return Result.retry()
        }

    }
    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }
}