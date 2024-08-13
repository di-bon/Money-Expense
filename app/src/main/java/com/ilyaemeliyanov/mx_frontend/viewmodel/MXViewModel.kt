package com.ilyaemeliyanov.mx_frontend.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.DocumentReference
import com.ilyaemeliyanov.mx_frontend.data.transactions.Transaction
import com.ilyaemeliyanov.mx_frontend.data.user.User
import com.ilyaemeliyanov.mx_frontend.data.wallets.Wallet
import kotlinx.coroutines.launch

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
    private val repository: MXRepository
) : ViewModel() {

    // Defining the state for user, wallets and transaction
    var user by mutableStateOf<User?>(null)
    var wallets by mutableStateOf<List<Wallet>>(emptyList())
    var transactions by mutableStateOf<List<Transaction>>(emptyList())

    // Defining the state for general info
    var balance by mutableStateOf(0.0f)
    var income by mutableStateOf(0.0f)
    var expenses by mutableStateOf(0.0f)

    // Defining selected items
    var selectedWallet by mutableStateOf<Wallet?>(null)

    suspend fun loadData(email: String) {
        viewModelScope.launch {
            loadUserByEmail(email)
        }
    }

    suspend fun loadUserByEmail(email: String) {
        viewModelScope.launch {
            repository.getUserByEmail(email) { fetchedUser ->
                user = fetchedUser
                viewModelScope.launch {
                    wallets = emptyList()
                    loadWalletsByUser(user)
                }
            }
        }
    }

    suspend fun loadWalletsByUser(user: User?) {
        if (user != null) {
            transactions = emptyList()
            user?.wallets?.forEach { walletRef ->
                repository.getWalletByDocRef(walletRef) { fetchedWallet ->
                    if (fetchedWallet != null) wallets += fetchedWallet
                    viewModelScope.launch {
                        loadTransactionsByWallet(fetchedWallet!!)
                    }
                }
            }
        }
    }

    suspend fun loadTransactionsByWallet(wallet: Wallet) {
        viewModelScope.launch {
            repository.getTransactionsByWallet(wallet) { fetchedTransactions ->
                if (fetchedTransactions.isNotEmpty()) transactions += fetchedTransactions
            }
        }
    }

    fun updateUser(u: User?) {
        if (u != null) {
            viewModelScope.launch {
                repository.updateUser(u) { newUser ->
                    user = newUser
                }
            }
        }
    }

    // TODO:
    //  1. Wallet CRUD operations both on ROOM and Firestore
    fun saveWallet(wallet: Wallet?) {
        if (wallet != null) {
            viewModelScope.launch {
                repository.saveWallet(wallet) { walletRef ->
                    // Update UI
                    if (walletRef != null) {
                        wallet.ref = walletRef
                        wallets += wallet
                    }
                    // Update User
                    user?.wallets = user?.wallets?.plus(walletRef!!) ?: emptyList<DocumentReference>()
                    updateUser(user)
                }
            }
        }
    }

    fun updateWallet(wallet: Wallet?) {
        if (wallet != null) {
            viewModelScope.launch {
                repository.updateWallet(wallet) {wallet ->
                   if (wallet != null) {
                       val newWallets = wallets.map {// Generate new list and replace the updated item
                           if (it.id == wallet.id) wallet else it
                       }
                       wallets = newWallets
                   }
                }
            }
        }
    }

    fun deleteWallet(wallet: Wallet?) {
        if (wallet != null) {
            viewModelScope.launch {
                repository.deleteWallet(wallet) { walletRef ->
                    if (walletRef != null) {
                        // Update UI
                        val newWallets = wallets.filter {
                            it.id != wallet.id
                        }
                        wallets = newWallets
                        // Update User
                        user?.wallets = user?.wallets?.filter { it != walletRef } ?: emptyList()
                        updateUser(user)

                        // Delete transactions
                        viewModelScope.launch {
                            repository.deleteTransactionsByWallet(walletRef)
                        }
                    }
                }
            }
        }
    }

    // TODO:
    //  2. Transaction CRUD operations both on ROOM and Firestore
    fun saveTransaction(transaction: Transaction?) {
        if (transaction != null) {
            viewModelScope.launch {
                // REMEMBER: pass the docref for the wallet and not the wallet itself
                repository.saveTransaction(transaction) { transactionRef ->
                    if (transactionRef != null) {
                        // Update local transactions
                        transaction.id = transactionRef.id
                        transactions += transaction

                        // Update the user.transactions references
                        user?.transactions = user?.transactions?.plus(transactionRef) ?: emptyList<DocumentReference>()
                        updateUser(user)
                    }
                }
            }
        }
    }


    fun updateTransaction(transaction: Transaction?) {
        if (transaction != null) {
            viewModelScope.launch {
                repository.updateTransaction(transaction)
            }
        }
    }

    fun deleteTransaction(transaction: Transaction?) {
        if (transaction != null) {
            viewModelScope.launch {
                repository.deleteTransaction(transaction) {transactionRef ->
                   if (transactionRef != null) {
                       transactions -= transaction

                       user?.transactions = user?.transactions?.minus(transactionRef) ?: emptyList()
                       updateUser(user)
                   }
                }
            }
        }
    }
}