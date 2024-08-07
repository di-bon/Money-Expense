package com.ilyaemeliyanov.mx_frontend.data.transactions

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ilyaemeliyanov.mx_frontend.data.wallets.Wallet
import java.util.Date

// TODO: add currency
@Entity
data class Transaction (
    @PrimaryKey val id: String,
    val label: String,
    val amount: Float,
    val date: Date,
    val wallet: Wallet
)