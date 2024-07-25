package com.ilyaemeliyanov.mx_frontend.utils

import java.util.Locale
import kotlin.math.abs

object StringFormatter {
    fun getFormattedAmount (amount: Float): String {
        return when {
            amount >= 0f -> {
                "+ \$%.2f".format(locale = Locale.US, amount)
            }
            else -> {
                "- \$%.2f".format(locale = Locale.US, abs(amount))
            }
        }
    }
}