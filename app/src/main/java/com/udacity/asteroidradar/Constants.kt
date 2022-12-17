package com.udacity.asteroidradar

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object Constants {
    const val API_QUERY_DATE_FORMAT = "YYYY-MM-dd"
    const val DEFAULT_END_DATE_DAYS = 7
    const val BASE_URL = "https://api.nasa.gov/"
    const val API = "DJQHmpr1Yl8oDPJGvy0KFHiTzdtORNu74wiSlTeR"

    @SuppressLint("WeekBasedYear")
    fun getDayDate():String{
        val calendar = Calendar.getInstance()
        val currentTime = calendar.time
        val dateFormat = SimpleDateFormat(API_QUERY_DATE_FORMAT, Locale.getDefault())
        return dateFormat.format(currentTime)
    }

    @SuppressLint("WeekBasedYear")
    fun getLastDate(): String{
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR,6)
        val currentTime = calendar.time
        val dateFormat =SimpleDateFormat(API_QUERY_DATE_FORMAT, Locale.getDefault())
        return dateFormat.format(currentTime)
    }

}