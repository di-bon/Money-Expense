package com.ilyaemeliyanov.mx_frontend.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilyaemeliyanov.mx_frontend.data.transactions.Transaction
import com.ilyaemeliyanov.mx_frontend.data.user.User
import com.ilyaemeliyanov.mx_frontend.data.user.UserRepository
import com.ilyaemeliyanov.mx_frontend.data.wallets.Wallet
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

// OLD STUFF TO INTEGRATE WITH THIS CLASS
//class MxViewModel : ViewModel() {
//    // TODO: implement checkLogIn()
//    fun checkLogIn(): Boolean {
//        return true
//    }
//
//    private val _uiState = MutableStateFlow(MxUiState())
//    val uiState: StateFlow<MxUiState> = _uiState
//
//}

class MXViewModel(
    private val repository: UserRepository
) : ViewModel() {
    // Defining the state
    val user = mutableStateOf<User?>(null)
    var wallets = mutableListOf<Wallet>()
    var transactions = mutableListOf<Transaction>()

    suspend fun loadData(email: String) {
        viewModelScope.launch {
            loadUserByEmail(email)
        }
    }

    suspend fun loadUserByEmail(email: String) {
        viewModelScope.launch {
            repository.getUserByEmail(email) { fetchedUser ->
                user.value = fetchedUser
                viewModelScope.launch {
                    loadWalletsByUser(user.value)
                }
            }
        }
    }

    suspend fun loadWalletsByUser(user: User?) {
        if (user != null) {
            wallets.clear()
            user?.wallets?.forEach { walletRef ->
                repository.getWalletByDocRef(walletRef) { fetchedWallet ->
                    if (fetchedWallet != null) wallets.add(fetchedWallet)
                    viewModelScope.launch {
                        loadTransactionsByWallet(fetchedWallet!!)
                    }
                }
            }
        }
    }

    suspend fun loadTransactionsByWallet(wallet: Wallet) {
        viewModelScope.launch {
            transactions.clear()
            repository.getTransactionsByWallet(wallet) { fetchedTransactions ->
                if (fetchedTransactions.isNotEmpty()) transactions.addAll(fetchedTransactions)
            }
        }
    }

}