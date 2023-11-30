package com.example.weatherforcastapp.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.annotation.RequiresApi
import android.recyclerview.widget.RecyclerView
import androidx.annotation.RequiresApi
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.WeatherForecastaApp.WeatherList
import com.example.weatherforcastapp.R
import com.example.weatherforcastapp.WeatherList
import java.text.SimpleDateFormat
import java.util.*


class ForeCastAdapter: RecyclerView.Adapter<ForeCastHolder>(){
private var listofforecast = listOf<WeatherList>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForeCastHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.upcomingforecastlist,parent,false)
    return ForeCastHolder(view)
    }

    override fun getItemCount(): Int {
        return listofforecast.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ForeCastHolder, position: Int) {
        val forecastObject=listofforecast[position]

        for (i in forecastObject.weather){
            holder.description.text = i.description!!
        }
        holder.humidity.text =forecastObject.main!!.humidity.toString()
        holder.windspeed.text = forecastObject.wind?.speed.toString()

        val temperatureFahrenheit = forecastObject.main?.temp
        val temperatureCelsius = (temperatureFahrenheit?.minus(273.15))
        val temperatureFormatted = String.format("% 2f",temperatureCelsius)

        holder.temp.text = "$temperatureFormatted C"

        val inputFormat = SimpleDataFormat ("yyyy-mm-dd  HH-mm",Locale.getDefault())
        val data = inputFormat.parse(forecastObject.dtTxt!!)
        val outputFormat = SimpleDateFormat ("d MMMM EEEE" , Locale.getDefault())
        val dateanddayname = outputFormat.format(date!!)

        holder.dateDayName.text = dateanddayname

        for (i in forecastObject.weather){
            if (i.icon=="01d"){
                holder.imageGraphic.setImageResource(R.drawable.oned)
                holder.smallIcon.setImageResource(R.drawable.oned)


            }
            if (i.icon=="01n"){
                holder.imageGraphic.setImageResource(R.drawable.onen)
                holder.smallIcon.setImageResource(R.drawable.onen)
            }

            if (i.icon=="02d"){
                holder.imageGraphic.setImageResource(R.drawable.twod)
                holder.smallIcon.setImageResource(R.drawable.twod)
            }
            if (i.icon=="02n"){
                holder.imageGraphic.setImageResource(R.drawable.twon)
                holder.smallIcon.setImageResource(R.drawable.twon)
            }

            if (i.icon=="03d"|| i.icon=="03n"){
                holder.imageGraphic.setImageResource(R.drawable.threedn)
                holder.smallIcon.setImageResource(R.drawable.threedn)
            }

            if (i.icon=="10d"){
                holder.imageGraphic.setImageResource(R.drawable.tend)
                holder.smallIcon.setImageResource(R.drawable.tend)
            }

            if (i.icon=="04d"|| i.icon=="04n"){
                holder.imageGraphic.setImageResource(R.drawable.fourdn)
                holder.smallIcon.setImageResource(R.drawable.fourdn)
            }

            if (i.icon=="09d"|| i.icon=="09n"){
                holder.imageGraphic.setImageResource(R.drawable.ninedn)
                holder.smallIcon.setImageResource(R.drawable.ninedn)
            }

            if (i.icon=="11d"|| i.icon=="11n"){
                holder.imageGraphic.setImageResource(R.drawable.elevend)
                holder.smallIcon.setImageResource(R.drawable.elevend)
            }

            if (i.icon=="13d"|| i.icon=="13n"){
                holder.imageGraphic.setImageResource(R.drawable.thirteend)
                holder.smallIcon.setImageResource(R.drawable.thirteend)
            }

            if (i.icon=="50d"|| i.icon=="50n"){
                holder.imageGraphic.setImageResource(R.drawable.fiftydn)
                holder.smallIcon.setImageResource(R.drawable.fiftydn)
            }
        }
    }

    fun setList(newlist:List<WeatherList>){
        this.listofforecast=newlist
    }
}
class ForeCastHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
 val imageGraphic :ImageView = itemView.findViewById(R.id.imageGraphic)
val description :TextView = itemView.findViewById(R.id.weatherDescr)
val humidity :ImageView = itemView.findViewById(R.id.humidity)
val windspeed :ImageView = itemView.findViewById(R.id.windspeed)
val temp :ImageView = itemView.findViewById(R.id.tempDisplayForeCast)
val smallIcon :ImageView = itemView.findViewById(R.id.smallIcon)
val dateDayName :ImageView = itemView.findViewById(R.id.dayDayText)}