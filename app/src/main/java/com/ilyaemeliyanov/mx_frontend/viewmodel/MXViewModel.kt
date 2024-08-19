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
import com.ilyaemeliyanov.mx_frontend.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import kotlin.math.abs

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

private const val TAG = "MXViewModel"

class MXViewModel(
    private val email: String,
    private val repository: MXRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    // Data to create a new transaction
    var transactionLabel: String by mutableStateOf("")
    var transactionDescription: String by mutableStateOf("")
    var transactionAmount: String by mutableStateOf("")
    var transactionDate: Date by mutableStateOf(Date())
    var transactionWalletName: String by mutableStateOf("")

//    var filteredAndSortedTransactions: List<Transaction> by mutableStateOf(emptyList())

    init {
        Log.d(TAG, "init() called")
        viewModelScope.launch {
            loadData(email)
        }
    }

    fun updateCurrentFilter(newFilter: String) {
        _uiState.update {
            it.copy(
                currentFilter = newFilter
            )
        }
//        filteredAndSortedTransactions = getFilteredAndSortedTransactions(
//            transactionList = transactions,
//            filter = _uiState.value.currentFilter,
//            sort = _uiState.value.currentFilter
//        )
    }

    fun updateCurrentSortingCriteria(newSortingCriteria: String) {
        _uiState.update {
            it.copy(
                currentSortingCriteria = newSortingCriteria,
            )
        }
//        filteredAndSortedTransactions = getFilteredAndSortedTransactions(
//            transactionList = transactions,
//            filter = _uiState.value.currentFilter,
//            sort = _uiState.value.currentFilter
//        )
    }

    fun createAndSaveTransaction() {
        val wallet = wallets.find { it.name == transactionWalletName }
        // TODO: validate input
        if (wallet != null) {
            val transaction = Transaction(
                id = "",
                label = transactionLabel,
                description = transactionDescription,
                amount = transactionAmount.toFloat(),
                date = transactionDate,
                wallet = wallet,
            )
            Log.d(TAG, "count before: ${transactions.size}")
            saveTransaction(transaction)
            Log.d(TAG, "count after: ${transactions.size}")
//            filteredAndSortedTransactions = getFilteredAndSortedTransactions(
//                transactionList = transactions,
//                filter = _uiState.value.currentFilter,
//                sort = _uiState.value.currentSortingCriteria
//            )
        }
    }

    // Defining the state for user, wallets and transaction
    var user by mutableStateOf<User?>(null)
    var wallets by mutableStateOf<List<Wallet>>(emptyList())
    var transactions by mutableStateOf(listOf<Transaction>())

    // Defining the state for general info
    var balance by mutableStateOf(0.0f)
    var income by mutableStateOf(0.0f)
    var expenses by mutableStateOf(0.0f)

    // Defining selected items
    var selectedWallet by mutableStateOf<Wallet?>(null)

    suspend fun loadData(email: String) {
//        viewModelScope.launch {
            loadUserByEmail(email)
//        }
    }

    suspend fun loadUserByEmail(email: String) {
//        viewModelScope.launch {
            repository.getUserByEmail(email) { fetchedUser ->
                user = fetchedUser
                viewModelScope.launch {
                    wallets = emptyList()
                    loadWalletsByUser(user)
                }
            }
//        }
    }

    suspend fun loadWalletsByUser(user: User?) {
        if (user != null) {
            transactions = emptyList()
            user.wallets.forEach { walletRef ->
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
//        viewModelScope.launch {
            repository.getTransactionsByWallet(wallet) { fetchedTransactions ->
                if (fetchedTransactions.isNotEmpty()) transactions += fetchedTransactions
            }
//            filteredAndSortedTransactions = getFilteredAndSortedTransactions(
//                transactionList = transactions,
//                filter = _uiState.value.currentFilter,
//                sort = _uiState.value.currentFilter
//            )
//        }
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

    // TODO: fix it, doesn't work
    private fun updateWalletBalance(wallet: Wallet, lastTransaction: Transaction) {
        viewModelScope.launch {
            wallet.amount += lastTransaction.amount
            repository.updateWallet(wallet) { }
        }
    }

    fun deleteWallet(wallet: Wallet?) {
        if (wallet != null) {
            viewModelScope.launch {
                repository.deleteWallet(wallet) { walletRef ->
                    if (walletRef != null) {
                        // Update User
                        user?.wallets = user?.wallets?.filter { it != walletRef } ?: emptyList()
                        val filteredTransactions = transactions?.filter { it.wallet.id != wallet.id } ?: emptyList()
                        user?.transactions = user?.transactions?.filter {
                            if (filteredTransactions != null) {
                                for (t in filteredTransactions) {
                                    if (it.id != t.id) false
                                }
                                true
                            }
                            false
                        } ?: emptyList()
                        updateUser(user)

                        // Update wallets UI
                        val newWallets = wallets.filter { it.id != wallet.id }
                        wallets = newWallets

                        // Update transactions UI
                        transactions = filteredTransactions

                        // Delete transactions
                        viewModelScope.launch {
                            repository.deleteTransactionsByWallet(walletRef)
                        }
                    }
                }
            }
        }
    }

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
//            updateWalletBalance(transaction.wallet, transaction) // TODO: leave this here? move it away?
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
                       Log.d(TAG, "Removing element form list")
                       transactions -= transaction

                       user?.transactions = user?.transactions?.minus(transactionRef) ?: emptyList()
                       updateUser(user)
                   }
                }
            }
        }
    }

    fun getFilteredAndSortedTransactions(
        transactionList: List<Transaction>,
        filter: String,
        sort: String
    ): List<Transaction> {
        var result: List<Transaction> = when (filter) {
            "Positive" -> transactionList.filter { it.amount >= 0 }
            "Negative" -> transactionList.filter { it.amount < 0 }
            else -> transactionList
        }

        result = when (sort) {
            "Oldest" -> result.sortedBy { it.date }
            "Biggest" -> result.sortedByDescending { abs(it.amount) }
            "Smallest" -> result.sortedBy { abs(it.amount) }
            else -> result.sortedByDescending { it.date }
        }

        return result
    }

    fun getLast10Transactions(transactions: List<Transaction>): List<Transaction> {
        return if (transactions.isNotEmpty() && selectedWallet != null) {
            transactions
                .filter { transaction ->
                    transaction.wallet == selectedWallet
                }
                .take(10)
        } else {
            transactions
                .take(10)
        }
            .sortedByDescending { it.date }
    }

    fun setSelectedWallet(item: String): Unit {
        selectedWallet = null
        for (wallet in wallets) {
            if (wallet.name == item) {
                selectedWallet = wallet
            }
        }
    }

    fun getIncome(transactions: List<Transaction>): Float {
        return transactions
            .fold(0.0) { sum, transaction ->
                if (transaction.amount >= 0) {
                    sum + transaction.amount
                } else {
                    sum
                }
            }.toFloat()
    }

    fun getExpenses(transactions: List<Transaction>): Float {
        return transactions
            .fold(0.0) { sum, transaction ->
                if (transaction.amount <= 0) {
                    sum + transaction.amount
                } else {
                    sum
                }
            }.toFloat()
    }

//    fun getBalance(transactions: List<Transaction>): Float {
//        return transactions
//            .fold(0.0) { sum, transaction ->
//                sum + transaction.amount
//            }.toFloat()
//    }

    fun getCurrentWalletTransactions(transactions: List<Transaction>): List<Transaction> {
        return if (selectedWallet != null) {
            transactions.filter { transaction -> transaction.wallet == selectedWallet }
        } else {
            transactions
        }
    }
}