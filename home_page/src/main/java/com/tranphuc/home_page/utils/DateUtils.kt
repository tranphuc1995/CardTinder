package com.tranphuc.home_page.utils

import java.util.*

object DateUtils {
    fun formatLongToDate(timeInMillisecond: Long): String {
        var result: String
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeInMillisecond
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)
        result = if(day<10){
            "0$day"
        }else{
            "" +day
        }
        result = if(month<10){
            "$result/0$month"
        }else{
            "$result/$month"
        }
        result = "$result/$year"
        return result

    }
}