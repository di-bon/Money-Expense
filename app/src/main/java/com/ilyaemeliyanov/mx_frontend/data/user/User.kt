package com.ilyaemeliyanov.mx_frontend.data.user

import com.google.firebase.firestore.DocumentReference

enum class Currency(val symbol: String) {
    US_DOLLAR("$"),
    EURO("€"),
    STERLING("£"),
    YEN("¥"),
    FRANC("CHF"),
    WON("₩"),
}

data class User (
    val id: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    var transactions: List<DocumentReference>,
    var wallets: List<DocumentReference>,
    var currency: Currency
) {
    override fun toString(): String {
        return "User=($email, $firstName, $lastName, $transactions, $wallets, $currency)"
    }
}