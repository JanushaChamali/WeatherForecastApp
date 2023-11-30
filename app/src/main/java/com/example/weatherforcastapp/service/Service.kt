package com.example.weatherforcastapp.service

import com.example.WeatherForecastaApp.Forecast
import com.example.weatherforcastapp.Utils
import okhttp3.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface Service {

    @GET("forecast?")
    fun getCurrentWeather(
        @Query("lat")
        lat:String,
        @Query("lon")
        lon:String,
        @Query("appid")
        appid:String=Utils.API_KEY
    )
    :Call<Forecast>

    @GET("forecast?")
    fun getWeatherByCity(
       @Query("q")
       city:String,
        @Query("appid")
        appid:String=Utils.API_KEY
    )
            :Call<Forecast>
}