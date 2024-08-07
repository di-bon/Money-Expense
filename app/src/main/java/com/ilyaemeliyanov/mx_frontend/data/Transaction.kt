package com.ilyaemeliyanov.mx_frontend.data

import java.util.Date

// TODO: add currency
data class Transaction (
    val label: String,
    val amount: Float,
    val date: Date,
    val wallet: Wallet
)