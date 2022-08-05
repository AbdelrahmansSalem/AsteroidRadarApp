package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.Database.AsteroidDatabase
import com.udacity.asteroidradar.Database.DatabaseAsteroids
import com.udacity.asteroidradar.Database.asDomainModel
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.NasaApi
import com.udacity.asteroidradar.api.asDatabaseModel
import com.udacity.asteroidradar.api.getToday
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class RepositoryAsteroid(private val asteroidDatabase: AsteroidDatabase) {


    suspend fun refreshData(){
        withContext(Dispatchers.IO){
            try {
                var asteroid=NasaApi.nasaApiService.getData(Constants.DATA_PATH, getToday(),Constants.API_KEY)
                var list_of_asterroid= parseAsteroidsJsonResult(JSONObject(asteroid))
                asteroidDatabase.databaseDAO.inserAll(*list_of_asterroid.asDatabaseModel())
            }
            catch (exception:Exception){
            }
        }

    }

     suspend fun get_image_of_today():PictureOfDay?{
        var pictureOfToday:PictureOfDay?
        withContext(Dispatchers.IO) {
            try {
                pictureOfToday = NasaApi.nasaApiService.getImageOfToday(Constants.IMAGE_PATH, Constants.API_KEY)
            }
            catch (e:Exception){
                pictureOfToday=null
            }

        }
        return pictureOfToday
    }
    suspend fun deleteYesterday(){
        withContext(Dispatchers.IO){
            asteroidDatabase.databaseDAO.deleteYestarday(getToday())
        }
    }

   suspend fun getTodayData():List<Asteroid>{
         var list:List<DatabaseAsteroids>
         withContext(Dispatchers.IO){
             list = asteroidDatabase.databaseDAO.getTodatData(getToday())
         }

         return list.asDomainModel()
    }
        suspend fun getAllData(): List<Asteroid>{
        var allData:List<DatabaseAsteroids>
        withContext(Dispatchers.IO){
            allData = asteroidDatabase.databaseDAO.getAllData()
        }


        return allData.asDomainModel()
    }
}