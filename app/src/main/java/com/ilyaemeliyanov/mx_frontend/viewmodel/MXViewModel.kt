package com.ilyaemeliyanov.mx_frontend.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.DocumentReference
import com.ilyaemeliyanov.mx_frontend.data.transactions.Transaction
import com.ilyaemeliyanov.mx_frontend.data.user.Currency
import com.ilyaemeliyanov.mx_frontend.data.user.User
import com.ilyaemeliyanov.mx_frontend.data.wallets.Wallet
import com.ilyaemeliyanov.mx_frontend.ui.UiState
import com.ilyaemeliyanov.mx_frontend.utils.TransactionType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID
import java.util.regex.Pattern
import kotlin.math.abs

private const val TAG = "MXViewModel"

class MXViewModel(
    private val repository: MXRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    lateinit var email: String

    // Data to create a new wallet
    var walletName by mutableStateOf("")
    var walletDescription by mutableStateOf("")
    var walletAmount by mutableStateOf("")

    // Data to create a new transaction
    var transactionLabel: String by mutableStateOf("")
    var transactionDescription: String by mutableStateOf("")
    var transactionAmount: String by mutableStateOf("")
    var transactionDate: Date by mutableStateOf(Date())
    var transactionWalletName: String by mutableStateOf("")
    var transactionType: TransactionType by mutableStateOf(TransactionType.EXPENSE)


//    var filteredAndSortedTransactions: List<Transaction> by mutableStateOf(emptyList())

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
        if (wallet != null) {
            val transactionValue = abs(transactionAmount.toFloat())
            val amount = if (transactionType == TransactionType.INCOME) transactionValue else -transactionValue
            val transaction = Transaction(
                id = "",
                label = transactionLabel,
                description = transactionDescription,
                amount = amount,
                date = transactionDate,
                wallet = wallet,
            )
            saveTransaction(transaction)
//            filteredAndSortedTransactions = getFilteredAndSortedTransactions(
//                transactionList = transactions,
//                filter = _uiState.value.currentFilter,
//                sort = _uiState.value.currentSortingCriteria
//            )
        }
    }

    // Defining the state for user, wallets and transaction
    var user by mutableStateOf<User?>(null)
    var wallets by mutableStateOf(listOf<Wallet>())
    var transactions by mutableStateOf(listOf<Transaction>())

    // Defining the state for general info
    var balance by mutableFloatStateOf(0.0f)
    var income by mutableFloatStateOf(0.0f)
    var expenses by mutableFloatStateOf(0.0f)

    // Defining selected items
    var selectedWallet by mutableStateOf<Wallet?>(null)

    suspend fun loadData(email: String) {
        loadUserByEmail(email)
    }

    private suspend fun loadUserByEmail(email: String) {
        repository.getUserByEmail(email) { fetchedUser ->
            user = fetchedUser
            viewModelScope.launch {
                wallets = emptyList()
                loadWalletsByUser(user)
            }
        }
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

    private suspend fun loadTransactionsByWallet(wallet: Wallet) {
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

    fun createAndSaveUser(email: String, firstName: String, lastName: String, password: String, currency: Currency) {
        val user = User(
            id = "",
            email = email,
            firstName = firstName,
            lastName = lastName,
            password = password,
            transactions = listOf(),
            wallets = listOf(),
            currency = currency
        )
        this.user = user
        repository.saveUser(user) {}
    }

    fun updateUserInfo(firstName: String, lastName: String) {
        val updatedUser = User(
            id = this.user?.id ?: "",
            email = this.user?.email ?: "",
            firstName = firstName,
            lastName = lastName,
            password = this.user?.password ?: "",
            transactions = this.user?.transactions ?: listOf(),
            wallets = this.user?.wallets ?: listOf(),
            currency = this.user?.currency ?: Currency.US_DOLLAR
        )
        updateUser(u = updatedUser)
    }

    fun updateUser(u: User?) {
        if (u != null) {
            viewModelScope.launch {
                repository.updateUser(u) { newUser ->
                    user = newUser?.copy()
                }
            }
            Log.d("New User", user.toString())
        }
    }

    suspend fun deleteUser(user: User?) {
        if (user != null) {
            val job = viewModelScope.launch {
                wallets.forEach {
                    deleteWallet(it)
                }
                repository.deleteUser(user) { }
            }
            job.join()
        }
    }

    fun createAndSaveWallet() {
        val wallet = Wallet(id = UUID.randomUUID().toString(), name = walletName, amount = walletAmount.toFloat(), description = walletDescription, ref = null)
        viewModelScope.launch {
            wallets += wallet
            repository.saveWallet(wallet) { walletRef ->
                // Update UI
                if (walletRef != null) {
                    wallet.id = walletRef.id // Remember that ID is not yet defined on UI creation
                    wallet.ref = walletRef // Remember to save the reference
                }
                // Update User
                user?.wallets = user?.wallets?.plus(walletRef!!) ?: emptyList()
                updateUser(user)
            }
        }
    }

    fun updateWallet(wallet: Wallet?) {
        if (wallet != null) {
            viewModelScope.launch {
                val newWallets = wallets.map {// Generate new list and replace the updated item
                    if (it.id == wallet.id) wallet else it
                }
                wallets = newWallets
                repository.updateWallet(wallet) { walletRef ->
                   if (walletRef != null) {
                       wallet.id = walletRef.id // when online update the ID
                       wallet.ref = walletRef // and the document reference to the wallet
                   }
                }
            }
        }
    }

    // TODO: fix it, doesn't work
//    private fun updateWalletBalance(wallet: Wallet, lastTransaction: Transaction) {
//        viewModelScope.launch {
//            wallet.amount += lastTransaction.amount
//            repository.updateWallet(wallet) { }
//        }
//    }

    suspend fun deleteWallet(wallet: Wallet?) {
        if (wallet != null) {
                // Update wallets UI
                val newWallets = wallets.filter { it.id != wallet.id }
                wallets = newWallets

                // Update transactions UI
                val filteredTransactions = transactions.filter { it.wallet.id != wallet.id }
                transactions = filteredTransactions

                val walletRefOut = repository.deleteWallet(wallet) { walletRef ->
                    Log.d(TAG, "walletRef: $walletRef")
                    if (walletRef != null) {
                        // Update User
                        user?.wallets = user?.wallets?.filter { it != walletRef } ?: emptyList()
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
                    }
                }
                if (walletRefOut != null) {
                    repository.deleteTransactionsByWallet(walletRefOut)
                    Log.d(TAG, "Transactions deleted from firestore")
                } else {
                    Log.d(TAG, "No transactions to be deleted from firestore -> walletRef is null")
                }
        }
    }

    private fun saveTransaction(transaction: Transaction?) {
        if (transaction != null) {
            viewModelScope.launch {
                // REMEMBER: pass the docref for the wallet and not the wallet itself
                transaction.id = UUID.randomUUID().toString() // assign a temporary ID until the user receives the response from Firebase
                transactions += transaction
                repository.saveTransaction(transaction) { transactionRef ->
                    if (transactionRef != null) {
                        // Update local transactions
                        transaction.id = transactionRef.id

                        // Update the user.transactions references
                        user?.transactions = user?.transactions?.plus(transactionRef) ?: emptyList()
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
                transactions -= transaction
                repository.deleteTransaction(transaction) {transactionRef ->
                   if (transactionRef != null) {
                       user?.transactions = user?.transactions?.filter { it.id != transactionRef.id } ?: emptyList()
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
        return transactionList
            .filter {
                when(filter) {
                    "Positive" -> it.amount >= 0
                    "Negative" -> it.amount < 0
                    else -> true
                }
            }
            .let { filtered ->
                when (sort) {
                    "Oldest" -> filtered.sortedBy { it.date }
                    "Biggest" -> filtered.sortedByDescending { abs(it.amount) }
                    "Smallest" -> filtered.sortedBy { abs(it.amount) }
                    else -> filtered.sortedByDescending { it.date }
                }
            }
    }

    fun getLast10Transactions(transactions: List<Transaction>): List<Transaction> {
        return if (transactions.isNotEmpty() && selectedWallet != null) {
            transactions
                .filter { transaction ->
                    transaction.wallet.id == selectedWallet?.id
                }
                .take(10)
        } else {
            transactions
                .take(10)
        }
            .sortedByDescending { it.date }
    }

    fun setSelectedWallet(item: String) {
        selectedWallet = null
        for (wallet in wallets) {
            if (wallet.name == item) {
                selectedWallet = wallet
            }
        }
        _uiState.update {
            it.copy(
                currentWallet = selectedWallet?.name
            )
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

    fun validateContent(content: String): Boolean {
        val contentPattern = "^[A-Za-z]+(?: [A-Za-z]+)*$"
        val pattern = Pattern.compile(contentPattern)
        val matcher = pattern.matcher(content)
        return matcher.matches()
    }

    fun validateEmail(email: String): Boolean {
        val emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"
        val pattern = Pattern.compile(emailPattern)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

    fun validatePassword(password: String): Boolean {
        return password.length >= 6
    }

    fun checkConfirmPassword(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }

    //    Encrypted Shared Preferences
//    private fun getEncryptedSharedPreferences(context: Context): SharedPreferences {
//        val masterKey = MasterKey.Builder(context)
//            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
//            .build()
//
//        return EncryptedSharedPreferences.create(
//            context,
//            "mx_prefs",
//            masterKey,
//            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
//            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
//        )
//    }

//    fun storeData(context: Context, key: String, value: String) {
//        val sharedPreferences = getEncryptedSharedPreferences(context)
//        val editor = sharedPreferences.edit()
//
//        // Store data
//        editor.putString(key, value)
//        editor.apply()
//    }
//
//    fun getData(context: Context, key: String): String? {
//        val sharedPreferences = getEncryptedSharedPreferences(context)
//
//        // Retrieve data
//        return sharedPreferences.getString(key, null)
//    }


    //    Paypal
//    fun generatePayPalAccessToken(clientId: String, clientSecret: String) {
//        viewModelScope.launch {
//            repository.getPayPalAccessToken(clientId, clientSecret) { token ->
//                if (token != null) {
//                    payPalAccessToken = token
//                }
//            }
//        }
//    }
//
//    fun getPayPalTransactions(accessToken: String) {
//        viewModelScope.launch {
//            repository.getPayPalTransactions(accessToken) { jsonString ->
//                if (jsonString != null) Log.d("MXViewModel", jsonString)
//            }
//        }
//    }
}