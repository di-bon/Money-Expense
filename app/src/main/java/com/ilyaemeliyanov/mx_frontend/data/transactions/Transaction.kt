package com.ilyaemeliyanov.mx_frontend.data.transactions

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentReference
import com.ilyaemeliyanov.mx_frontend.data.wallets.Wallet
import java.util.Date

// TODO: add currency
@Entity
data class Transaction (
    @PrimaryKey var id: String,
    val label: String,
    val description: String,
    val amount: Float,
    val date: Date,
    val wallet: Wallet,
) {
    override fun toString(): String {
        return "Transaction=($id, $label, $amount, $date, ${wallet.toString()})"
    }
}