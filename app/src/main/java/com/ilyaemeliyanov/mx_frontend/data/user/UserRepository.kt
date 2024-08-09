package com.ilyaemeliyanov.mx_frontend.data.user

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.ilyaemeliyanov.mx_frontend.data.transactions.Transaction
import com.ilyaemeliyanov.mx_frontend.data.wallets.Wallet
import kotlinx.coroutines.tasks.await
import java.sql.Timestamp
import java.util.Date

class UserRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val usersCollection = firestore.collection("users")
    private val transactionsCollection = firestore.collection("transactions")
    private val walletsCollection = firestore.collection("wallets")

    suspend fun getUserByEmail(email: String, callback: (User?) -> Unit) {
        val documentSnapshot = usersCollection.whereEqualTo("email", email).get().await().documents[0]
        val id = documentSnapshot.id
        val data = documentSnapshot.data
        val user = User(
            id = id,
            email = email,
            firstName = data?.get("firstName") as? String ?: "null",
            lastName = data?.get("lastName") as? String ?: "null",
            password = data?.get("password") as? String ?: "null",
            wallets = data?.get("wallets") as? List<DocumentReference> ?: emptyList(),
            transactions = data?.get("transactions") as? List<DocumentReference> ?: emptyList()
        )
        callback(user)
    }

    suspend fun getWalletByDocRef(ref: DocumentReference, callback: (Wallet?) -> Unit) {
        val documentSnapshot = ref.get().await()
        val id = documentSnapshot.id
        val data = documentSnapshot.data
        val wallet = Wallet(
            id = id,
            name = data?.get("name") as? String ?: "null",
            amount = data?.get("amount") as? Float ?: 0.0f,
            description = data?.get("description") as? String ?: "No description provided..."
        )
        callback(wallet)
    }

    suspend fun getTransactionsByWallet(wallet: Wallet, callback: (List<Transaction>) -> Unit) {
        val documentSnapshots = transactionsCollection.whereEqualTo("wallet", wallet.id).get().await()
        val transactions: MutableList<Transaction> = mutableListOf()
        for (document in documentSnapshots) {
            val id = document.id
            val data = document.data
            transactions.add(Transaction(
                id = id,
                label = data.get("label") as? String ?: "null",
                description = data.get("description") as? String ?: "null",
                amount = document.getDouble("amount")?.toFloat() ?: 0.0f,
                date = document.getTimestamp("date")?.toDate() ?: Date(),
                wallet = wallet
           ))
        }
        callback(transactions)
    }


}


