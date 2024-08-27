package com.ilyaemeliyanov.mx_frontend.data.wallets

import com.google.firebase.firestore.DocumentReference

data class Wallet (
    var id: String,
    val name: String,
    var amount: Float,
    val description: String?,
    var ref: DocumentReference?,
) {
    override fun toString(): String {
        return "Wallet=($id, $name, $amount, $description)"
    }
}