package com.ilyaemeliyanov.mx_frontend.data.transactions

import com.ilyaemeliyanov.mx_frontend.data.wallets.Wallet
import java.util.Date

data class Transaction (
    var id: String,
    val label: String,
    val description: String,
    val amount: Float,
    val date: Date,
    val wallet: Wallet,
) {
    override fun toString(): String {
        return "Transaction=($id, $label, $amount, $date, $wallet)"
    }
}