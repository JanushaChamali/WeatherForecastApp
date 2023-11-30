package com.example.weatherforcastapp.mvvm

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.WeatherForecastaApp.WeatherList
import com.example.weatherforcastapp.MyApplication
import com.example.weatherforcastapp.SharePrefs

import com.example.weatherforcastapp.service.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class WeatherVm : ViewModel() {
    val todayWeatherLiveData = MutableLiveData<List<WeatherList>>()
    val forecastWeatherLiveData = MutableLiveData<List<WeatherList>>()
    val closetorexactlysameweatherdate = MutableLiveData<WeatherList?>()
    val cityName = MutableLiveData<String>()
    val context = MyApplication.instance


    @RequiresApi(Build.VERSION_CODES.O)
    fun getWeather(city: String? = null) = viewModelScope.launch(Dispatchers.IO) {
        val todayWeatherList = mutableListOf<WeatherList>()
        val currentDateTime = LocalDateTime.now()
        val currentDatePattern = currentDateTime.format(DateTimeFormatter.ofPattern("YYYY-MM-dd"))
        val sharePrefs = SharePrefs.getInstance(context)
        val lat = sharePrefs.getvalue("lat").toString()
        val lon = sharePrefs.getvalue("lon").toString()

        Log.e("CODE VM", "$lat $lon")

        val call = if (city != null) {
            RetrofitInstance.api.getWeatherByCity(city)

        } else {
            RetrofitInstance.api.getCurrentWeather(lat, lon)
        }
        val response = call.execute()

        if (response.isSuccessful) {
            val weatherList = response.body()?.weatherList
            cityName.postValue(response.body()?.city!!.name)

            val presentDate = currentDatePattern
            weatherList?.forEach { weather ->
                // Separate all the weather object that has the date of today
                if (weather.dtTxt!!.split("\\s".toRegex()).contains(presentDate)) {
                    todayWeatherList.add(weather)
                }
            }

            // if the API Time closet to the system's time display that
            //if API time matches the system time also display that
            val closestWeather = findClosestWeather(todayWeatherList)
            closetorexactlysameweatherdate.postValue(closestWeather)
            todayWeatherLiveData.postValue(todayWeatherList)


        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getForecastUpcoming(city: String? = null) = viewModelScope.launch(Dispatchers.IO) {
        val forecastWeatherList = mutableListOf<WeatherList>()
        val currentDateTime = LocalDateTime.now()
        val currentDatePattern = currentDateTime.format(DateTimeFormatter.ofPattern("YYYY-MM-dd"))
        val sharePrefs = SharePrefs.getInstance(context)
        val lat = sharePrefs.getvalue("lat").toString()
        val lon = sharePrefs.getvalue("lon").toString()

        val call = if (city != null) {
            RetrofitInstance.api.getWeatherByCity(city)

        } else {
            RetrofitInstance.api.getCurrentWeather(lat, lon)
        }

        val response = call.execute()

        if (response.isSuccessful) {
            val weatherList = response.body()?.weatherList
            cityName.postValue(response.body()?.city!!.name)

            val presentDate = currentDatePattern

            weatherList?.forEach { weather ->
                if (!weather.dtTxt!!.split("\\s".toRegex()).contains(presentDate)) {

                    if (weather.dtTxt!!.substring(11, 16) == "12.00") {
                        forecastWeatherList.add(weather)
                    }
                }
            }

            forecastWeatherLiveData.postValue(forecastWeatherList)


        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun findClosestWeather(weatherList: List<WeatherList>): WeatherList? {
        val systemTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
        var closestWeather: WeatherList? = null
        var minTimeDifference = Int.MAX_VALUE

        for (weather in weatherList) {
            val weatherTime = weather.dtTxt!!.substring(11, 16)
            val timeDifference = Math.abs(timeToMinutes(weatherTime) - timeToMinutes(systemTime))

            if (timeDifference < minTimeDifference) {
                minTimeDifference = timeDifference
                closestWeather = weather
            }
        }
        return closestWeather
    }

    private fun timeToMinutes(time: String): Int {
        val parts = time.split(":")
        return parts[0].toInt() * 60 + parts[1].toInt()
    }
}