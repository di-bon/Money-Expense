package com.ilyaemeliyanov.mx_frontend.data.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ilyaemeliyanov.mx_frontend.data.transactions.Transaction
import com.ilyaemeliyanov.mx_frontend.data.wallets.Wallet

@Entity
data class User (
    @PrimaryKey val id: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val password: String,
    val transactions: List<Transaction>,
    val wallets: List<Wallet>
)