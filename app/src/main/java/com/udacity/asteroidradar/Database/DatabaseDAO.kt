package com.udacity.asteroidradar.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.Asteroid
import kotlinx.coroutines.selects.select

@Dao
interface DatabaseDAO {

    @Query("select *from Asteroids  order by closeApproachDate Asc")
    fun getAllData(): List<DatabaseAsteroids>

    @Query("delete from Asteroids where closeApproachDate <:today")
    fun deleteYestarday(today:String)
//
    @Query("select *from asteroids where closeApproachDate =:today")
    fun getTodatData(today:String):List<DatabaseAsteroids>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserAll(vararg databasePlanet: DatabaseAsteroids)
}