package com.ilyaemeliyanov.mx_frontend.data.wallets

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Wallet (
    @PrimaryKey val id: String,
    val name: String,
    val amount: Float,
    val description: String?
) {
    override fun toString(): String {
        return "Wallet=($id, $name, $amount, $description)"
    }
}