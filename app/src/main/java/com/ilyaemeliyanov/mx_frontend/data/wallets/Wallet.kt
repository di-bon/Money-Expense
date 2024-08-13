package com.ilyaemeliyanov.mx_frontend.data.wallets

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentReference
import com.ilyaemeliyanov.mx_frontend.data.transactions.Transaction

@Entity
data class Wallet (
    @PrimaryKey val id: String,
    val name: String,
    val amount: Float,
    val description: String?,
    var ref: DocumentReference?,
) {
    override fun toString(): String {
        return "Wallet=($id, $name, $amount, $description)"
    }
}