package com.ilyaemeliyanov.mx_frontend.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.abs

object StringFormatter {
    fun getFormattedAmount (amount: Float): String {
        return "%.2f".format(locale = Locale.US, abs(amount))
    }

    fun getDateFromString(dateString: String, pattern: String = "yyyy-MM-dd"): Date? {
        val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        return try {
            dateFormat.parse(dateString)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getStringFromDate(date: Date, pattern: String = "yyyy-MM-dd"): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(date)
    }
}