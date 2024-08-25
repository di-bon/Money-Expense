package com.ilyaemeliyanov.mx_frontend.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

private const val TAG = "MXAuthViewModel"

class MXAuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    // LiveData for observing authentication state
    val user = auth.currentUser

    fun signUp(email: String, password: String, firstName: String, lastName: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {

                            onResult(true, null)
                        } else {
                            onResult(false, task.exception?.message)
                        }
                    }
            } catch (e: Exception) {
                onResult(false, e.message)
            }
        }
    }

    fun logIn(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            onResult(true, null)
                        } else {
                            onResult(false, task.exception?.message)
                        }
                    }
            } catch (e: Exception) {
                onResult(false, e.message)
            }
        }
    }

    fun changePassword(newPassword: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                user!!.updatePassword(newPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "User password updated.")
                        onResult(true, null)
                    } else {
                        onResult(false, task.exception?.message)
                    }
                }
            } catch (e: Exception) {
                onResult(false, e.message)
            }
        }
    }

    fun signOut() {
        auth.signOut()
    }

    fun deleteUser() {
        if (user != null) {
            user
                .delete()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "User account deleted from firebase auth.")
                    } else {
                        Log.d(TAG, "Error while trying to delete user ${user.email} from firebase auth")
                    }
                }
        } else {
            Log.d(TAG, "User is null!")
        }
    }
}