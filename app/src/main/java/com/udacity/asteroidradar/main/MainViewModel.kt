package com.udacity.asteroidradar.main

import android.app.Application
import android.content.Context
import android.text.format.DateFormat
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.Database.AsteroidDatabase
import com.udacity.asteroidradar.Database.getDatabase
import com.udacity.asteroidradar.PictureOfDay

import com.udacity.asteroidradar.api.NasaApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.repository.RepositoryAsteroid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.*

enum class dataFilter(val value :String){
    ALL_DATA("all"),
    TODAY_DATA("today"),
    SAVED_DATA("saved")
}

class MainViewModel(application: Application) : AndroidViewModel(application) {

    var database= getDatabase(application)
    var repositoryAsteroid=RepositoryAsteroid(database)


    private var _filterType=MutableLiveData<dataFilter>()
    val filterType:LiveData<dataFilter>get() = _filterType

    private var _list=MutableLiveData<List<Asteroid>>()
    val list:LiveData<List<Asteroid>>get() = _list



    private var _pictureOfDay=MutableLiveData<PictureOfDay>()
    val pictureOfDay:LiveData<PictureOfDay>get()=_pictureOfDay


    private var _navigate_to_details=MutableLiveData<Asteroid>()
    val navigate_to_details:LiveData<Asteroid>get() = _navigate_to_details


    init {
        viewModelScope.launch{
            repositoryAsteroid.refreshData()
            _list.value=repositoryAsteroid.getTodayData()
           _pictureOfDay.value=repositoryAsteroid.get_image_of_today()
        }
        _navigate_to_details.value=null
        _filterType.value=dataFilter.TODAY_DATA

    }


    fun navigateToDetails(asteroid: Asteroid){
        _navigate_to_details.value=asteroid
    }
    fun navigateToDetailsDone()
    {
        _navigate_to_details.value=null
    }

    fun getFilteredData(filter: dataFilter){
        _filterType.value=filter
    }

    fun setList(){
        viewModelScope.launch{
            if (_filterType.value==dataFilter.TODAY_DATA){
                _list.value=repositoryAsteroid.getTodayData()
            }
            else{
                _list.value=repositoryAsteroid.getAllData()
            }
        }


    }

}