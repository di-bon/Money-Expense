package com.ilyaemeliyanov.mx_frontend.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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


    // TODO:
    //  1. Wallet CRUD operations both on ROOM and Firestore
    //  2. Transaction CRUD operations both on ROOM and Firestore
}