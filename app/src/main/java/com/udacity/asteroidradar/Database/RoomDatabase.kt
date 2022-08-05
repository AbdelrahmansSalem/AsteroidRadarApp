package com.udacity.asteroidradar.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.Asteroid
import okhttp3.internal.Version

@Database(entities = [DatabaseAsteroids::class], version = 1)
abstract class AsteroidDatabase : RoomDatabase() {

    abstract val databaseDAO: DatabaseDAO


}
private lateinit var INSTANCE: AsteroidDatabase

fun getDatabase(context: Context): AsteroidDatabase {
    synchronized(AsteroidDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                AsteroidDatabase::class.java, "Asteroids"
            ).build()
        }
    }

    return INSTANCE
}