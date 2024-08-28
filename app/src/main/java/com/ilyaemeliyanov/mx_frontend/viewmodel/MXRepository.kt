package com.ilyaemeliyanov.mx_frontend.viewmodel

import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.ilyaemeliyanov.mx_frontend.data.transactions.Transaction
import com.ilyaemeliyanov.mx_frontend.data.user.Currency
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
        val documentSnapshotList = usersCollection.whereEqualTo("email", email).get().await()
        if (documentSnapshotList.isEmpty) {
            Log.d(TAG, "Snapshots empty")
            callback(null)
            return
        }
        val documentSnapshot = documentSnapshotList.documents[0]
        val id = documentSnapshot.id
        val data = documentSnapshot.data
        val currencyName = data?.get("currency") as? String
        val user = User(
            id = id,
            email = email,
            firstName = data?.get("firstName") as? String ?: "null",
            lastName = data?.get("lastName") as? String ?: "null",
            wallets = (documentSnapshot.get("wallets") as? List<DocumentReference>)?.toList() ?: emptyList(),
            transactions = (documentSnapshot.get("transactions") as? List<DocumentReference>)?.toList() ?: emptyList(),
            currency = Currency.entries.firstOrNull { it.name == currencyName } ?: Currency.US_DOLLAR
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
        val userData = mutableMapOf<String, Any?>().apply {
            put("firstName", user.firstName)
            put("lastName", user.lastName)
            put("email", user.email)
            put("transactions", user.transactions)
            put("wallets", user.wallets)
        }
        usersCollection
            .add(userData)
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
        val userData = mutableMapOf<String, Any?>().apply {
            put("firstName", user.firstName)
            put("lastName", user.lastName)
            put("email", user.email)
            put("transactions", user.transactions)
            put("wallets", user.wallets)
            put("currency", user.currency)
        }

        docRef
            .set(userData)
            .addOnSuccessListener {
                Log.d(TAG, "User item updated with ID: ${docRef.id}")
                callback(user)
            }
            .addOnFailureListener {e ->
                Log.w(TAG, "Error updating user item", e)
                callback(null)
            }
    }

    fun deleteUser(user: User, callback: (DocumentReference?) -> Unit) {
        val docRef = usersCollection.document(user.id)
        docRef
            .delete()
            .addOnSuccessListener {
                Log.d(TAG, "User ${user.email} successfully deleted")
                callback(docRef)
            }
            .addOnFailureListener {
                Log.d(TAG, "Error while trying to delete user ${user.email}")
                callback(null)
            }
    }

    fun saveWallet(wallet: Wallet, callback: (DocumentReference?) -> Unit) {
        val walletData = mutableMapOf<String, Any?>().apply {
            put("name", wallet.name)
            put("description", wallet.description)
            put("amount", wallet.amount)
        }
        walletsCollection
            .add(walletData)
            .addOnSuccessListener { docRef ->
                Log.d(TAG, "Wallet item added with ID: ${docRef.id}")
                callback(docRef)
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding wallet item", e)
                callback(null)
            }
    }

    fun updateWallet(wallet: Wallet, callback: (DocumentReference?) -> Unit) {
        val docRef = walletsCollection.document(wallet.id)
        val walletData = mutableMapOf<String, Any?>().apply {
            put("name", wallet.name)
            put("description", wallet.description)
            put("amount", wallet.amount)
        }

        docRef
            .set(walletData)
            .addOnSuccessListener {
                Log.d(TAG, "Wallet item updated with ID: ${docRef.id}")
                callback(docRef)
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error updating wallet item", e)
                callback(null)
            }
    }

    suspend fun deleteWallet(wallet: Wallet, callback: (DocumentReference?) -> Unit): DocumentReference {
        val docRef = walletsCollection.document(wallet.id)
        val result: DocumentReference = docRef

        docRef
            .delete()
            .addOnSuccessListener {
                Log.d(TAG, "Wallet item deleted with ID: ${docRef.id}")
                callback(docRef)
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error deleting wallet item", e)
                callback(null)
            }
            .await()

        return result
    }

    fun saveTransaction(transaction: Transaction, callback: (DocumentReference?) -> Unit) {
        val transactionData = mutableMapOf<String, Any?>().apply {
            put("label", transaction.label)
            put("description", transaction.description)
            put("amount", transaction.amount)
            put("date", transaction.date)
            put("wallet", transaction.wallet.ref)
        }
        transactionsCollection
            .add(transactionData)
            .addOnSuccessListener { docRef ->
                Log.d(TAG, "Transaction item added with ID: ${docRef.id}")
                callback(docRef)
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding transaction item", e)
                callback(null)
            }
    }

    fun updateTransaction(transaction: Transaction) {
        val docRef = transactionsCollection.document(transaction.id)
        val transactionData = mutableMapOf<String, Any?>().apply {
            put("label", transaction.label)
            put("description", transaction.description)
            put("amount", transaction.amount)
            put("date", transaction.date)
            put("wallet", transaction.wallet.ref)
        }

        docRef
            .set(transactionData)
            .addOnSuccessListener {
                Log.d(TAG, "Transaction item updated with ID: ${docRef.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error updating transaction item", e)
            }
    }

    fun deleteTransaction(transaction: Transaction, callback: (DocumentReference?) -> Unit) {
        val docRef = transactionsCollection.document(transaction.id)

        docRef
            .delete()
            .addOnSuccessListener {
                Log.d(TAG, "Transaction item deleted")
                callback(docRef)
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error deleting transaction item", e)
                callback(null)
            }
    }

    suspend fun deleteTransactionsByWallet(walletRef: DocumentReference) {
        val query = transactionsCollection
            .whereEqualTo("wallet", walletRef)
            .get()
            .await()

        for (document in query.documents) {
            transactionsCollection.document(document.id).delete().await()
        }
    }

//    suspend fun getPayPalAccessToken(clientId: String, clientSecret: String, callback: (String?) -> Unit) {
//        withContext(Dispatchers.IO) {
//            val client = OkHttpClient()
//            val credential = Credentials.basic(clientId, clientSecret)
//            val request = Request.Builder()
//                .url("https://api-m.sandbox.paypal.com/v1/oauth2/token")
//                .post(FormBody.Builder()
//                    .add("grant_type", "client_credentials")
//                    .add("ignoreCache", "true")
//                    .add("return_authn_schemes", "true")
//                    .add("return_client_metadata", "true")
//                    .add("return_unconsented_scopes", "true")
//                    .build()
//                )
//                .addHeader("Authorization", credential)
//                .addHeader("Content-Type", "application/x-www-form-urlencoded")
//                .build()
//
//            val response = client.newCall(request).execute()
//            val jsonResponse = JSONObject(response.body?.string() ?: "")
//            Log.d("Response", jsonResponse.toString())
//            callback(jsonResponse.getString("access_token"))
//        }
//        callback(null)
//    }

//    suspend fun getPayPalTransactions(accessToken: String, callback: (String?) -> Unit) {
//        withContext(Dispatchers.IO) {
//            val client = OkHttpClient()
//            val request = Request.Builder()
//                .url("https://api-m.sandbox.paypal.com/v1/reporting/transactions?fields=transaction_info&start_date=2024-03-20T11:59:59.999Z&end_date=2024-03-20T23:59:00.000Z") // test query, replace in production
//                .get()
//                .addHeader("Authorization", "Bearer $accessToken")
//                .build()
//
//            val response = client.newCall(request).execute()
//            val jsonResponse = JSONObject(response.body?.string() ?: "")
//            callback(jsonResponse.toString())
//        }
//        callback(null)
//    }
}


