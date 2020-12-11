package com.tranphuc.home_page.utils

import android.text.format.DateFormat
import java.util.*

object DateUtils {
    fun formatLongToDate(timeInMillisecond: Long): String {
        return DateFormat.format("dd/MM/yyyy", Date(timeInMillisecond)).toString()
    }
}