package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.text.SimpleDateFormat
import java.util.*


var moshi= Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private var retrofit= Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Constants.BASE_URL)
    .build()

fun getToday():String{
    val sdf = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
    var currentDate= sdf.format(Date())
    return currentDate
}

interface NasaApiService{
    @GET("{path}")
    suspend fun getData(@Path("path") path:String, @Query("start_date")start_date:String, @Query("api_key")api_key:String):String

    @GET("{path}")
    suspend fun getImageOfToday(@Path("path") path:String,@Query("api_key")api_key:String):PictureOfDay

}

object NasaApi{
    val nasaApiService :NasaApiService by lazy {
        retrofit.create(NasaApiService::class.java)
    }
}