package com.ilyaemeliyanov.mx_frontend.viewmodel

import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.ilyaemeliyanov.mx_frontend.data.transactions.Transaction
import com.ilyaemeliyanov.mx_frontend.data.user.User
import com.ilyaemeliyanov.mx_frontend.data.wallets.Wallet
import kotlinx.coroutines.tasks.await
import java.util.Date

private const val TAG = "MXRepository"

class MXRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val usersCollection = firestore.collection("users")
    private val walletsCollection = firestore.collection("wallets")
    private val transactionsCollection = firestore.collection("transactions")

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
            wallets = (documentSnapshot.get("wallets") as? List<DocumentReference>)?.toList() ?: emptyList(),
            transactions = (documentSnapshot.get("transactions") as? List<DocumentReference>)?.toList() ?: emptyList(),
        )
        callback(user)
    }

    suspend fun getWalletByDocRef(ref: DocumentReference, callback: (Wallet?) -> Unit) {
        val document = ref.get().await()
        val id = document.id
        val data = document.data
        val wallet = Wallet(
            id = id,
            name = data?.get("name") as? String ?: "null",
            amount = document?.getDouble("amount")?.toFloat() ?: 0.0f,
            description = data?.get("description") as? String ?: "No description provided...",
            ref = document.reference
        )
        callback(wallet)
    }

    suspend fun getTransactionsByWallet(wallet: Wallet, callback: (List<Transaction>) -> Unit) {
        val walletRef = walletsCollection.document(wallet.id)

        val documentSnapshots = transactionsCollection.whereEqualTo("wallet", walletRef).get().await()
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
                wallet = wallet,
           ))
        }
        callback(transactions)
    }

    fun saveUser(user: User, callback: (DocumentReference?) -> Unit) {
        usersCollection
            .add(user)
            .addOnSuccessListener { userRef ->
                Log.d(TAG, "User saved with id ${userRef.id}")
                callback(userRef)
            }
            .addOnFailureListener { e ->
                Log.d(TAG, "Error updating user item ${e.message}")
                callback(null)
            }
    }

    fun updateUser(user: User, callback: (User?) -> Unit) {
        val docRef = usersCollection.document(user.id)

        Log.d("MXRepository", user.transactions.toString())

        // Update the existing document in collection with the specified user
        docRef
            .set(user)
            .addOnSuccessListener {
                Log.d("Firestore", "User item updated with ID: ${docRef.id}")
                callback(user)
            }
            .addOnFailureListener {e ->
                Log.w("Firestore", "Error updating user item", e)
                callback(null)
            }
    }

    fun saveWallet(wallet: Wallet, callback: (DocumentReference?) -> Unit) {
        walletsCollection
            .add(wallet)
            .addOnSuccessListener { docRef ->
                Log.d("Firestore", "Wallet item added with ID: ${docRef.id}")
                callback(docRef)
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error adding wallet item", e)
                callback(null)
            }
    }

    fun updateWallet(wallet: Wallet, callback: (Wallet?) -> Unit) {
        val docRef = walletsCollection.document(wallet.id)

        // Update the existing document in collection with the specified wallet
        docRef
            .set(wallet)
            .addOnSuccessListener {
                Log.d("Firestore", "Wallet item updated with ID: ${docRef.id}")
                callback(wallet)
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error updating wallet item", e)
                callback(null)
            }
    }

    fun deleteWallet(wallet: Wallet, callback: (DocumentReference?) -> Unit) {
        val docRef = walletsCollection.document(wallet.id)

        // Update the existing document in collection with the specified wallet
        docRef
            .delete()
            .addOnSuccessListener {
                Log.d("Firestore", "Wallet item deleted with ID: ${docRef.id}")
                callback(docRef)
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error deleting wallet item", e)
                callback(null)
            }
    }

    fun saveTransaction(transaction: Transaction, callback: (DocumentReference?) -> Unit) {
        val transactionData = mutableMapOf<String, Any?>().apply {
            put("label", transaction.label)
            put("description", transaction.description)
            put("amount", transaction.amount)
            put("date", transaction.date)
            put("wallet", transaction.wallet.ref) // replace the wallet with its reference for future linkage
        }
        transactionsCollection
            .add(transactionData)
            .addOnSuccessListener { docRef ->
                Log.d("Firestore", "Transaction item added with ID: ${docRef.id}")
                callback(docRef)
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error adding transaction item", e)
                callback(null)
            }
    }

    fun updateTransaction(transaction: Transaction) {
        val docRef = transactionsCollection.document(transaction.id)

        // Update the existing document in collection with the specified wallet
        docRef
            .set(transaction)
            .addOnSuccessListener {
                Log.d("Firestore", "Transaction item updated with ID: ${docRef.id}")
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error updating transaction item", e)
            }
    }

    fun deleteTransaction(transaction: Transaction, callback: (DocumentReference?) -> Unit) {
        val docRef = transactionsCollection.document(transaction.id)

        // Update the existing document in collection with the specified wallet
        docRef
            .delete()
            .addOnSuccessListener {
                Log.d("Firestore", "Transaction item deleted")
                callback(docRef)
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error deleting transaction item", e)
                callback(null)
            }
    }

    suspend fun deleteTransactionsByWallet(walletRef: DocumentReference) {
        val query = transactionsCollection
            .whereEqualTo("wallet", walletRef)
            .get()
            .await()

        // Iterate over the documents and delete them
        for (document in query.documents) {
            transactionsCollection.document(document.id).delete().await()
        }
    }
}


