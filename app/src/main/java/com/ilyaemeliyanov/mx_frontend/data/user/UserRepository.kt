package com.ilyaemeliyanov.mx_frontend.data.user

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class UserRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val usersCollection = firestore.collection("users")

    fun getUserByEmail(email: String, callback: (User?) -> Unit) {
        usersCollection
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { result ->
                val document = result.documents[0]
                val id = document.id
                val data = document.data
                Log.d(TAG, result.documents[0].data.toString())
//                callback(document.toObject(User::class.java)?.copy(id = document.id))
                callback(User(
                    id = id,
                    firstName = data?.get("firstname") as? String ?: "null",
                    lastName = data?.get("lastName") as? String ?: "null",
                    email = data?.get("email") as? String ?: "null",
                    password = data?.get("password") as? String ?: "null",
                    wallets = emptyList(),
                    transactions = emptyList(),
                ))
            }
            .addOnFailureListener {exception ->
                callback(null)
            }
    }
}


