package com.ilyaemeliyanov.mx_frontend.data

import java.util.Date

// TODO: add currency
data class Transaction (
    val title: String,
    val amount: Float,
    val date: Date,
    val description: String?
)