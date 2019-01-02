package com.ccooy.gankio.utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat

object TimeUtil {

    fun dateFormat(timestamp: String?): String {
        if (timestamp == null) {
            return "unknown"
        }
        @SuppressLint("SimpleDateFormat") val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS")
        @SuppressLint("SimpleDateFormat") val outputFormat = SimpleDateFormat("yyyy-MM-dd")

        try {
            val date = inputFormat.parse(timestamp)
            return outputFormat.format(date)
        } catch (e: ParseException) {
            return "unknown"
        }

    }
}
