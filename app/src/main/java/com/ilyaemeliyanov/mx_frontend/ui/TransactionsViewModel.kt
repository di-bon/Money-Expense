//package com.ilyaemeliyanov.mx_frontend.ui
//
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.ViewModel
//import com.ilyaemeliyanov.mx_frontend.data.transactions.Transaction
//import com.ilyaemeliyanov.mx_frontend.viewmodel.MXViewModel
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.update
//import java.util.Date
//import kotlin.math.abs
//
//class TransactionsViewModel(
//    val generalViewModel: MXViewModel
//) : ViewModel() {
//    private val _uiState = MutableStateFlow(UiState())
//    val uiState: StateFlow<UiState> = _uiState
//
//    // Data to create a new transaction
//    var label: String by mutableStateOf("")
//    var description: String by mutableStateOf("")
//    var amount: String by mutableStateOf("")
//    var date: Date by mutableStateOf(Date())
//
//    fun updateCurrentWallet(walletName: String) {
//        _uiState.update {
//            it.copy(
//                currentWalletName = walletName
//            )
//        }
//    }
//
//    fun updateCurrentFilter(newFilter: String) {
//        _uiState.update {
//            it.copy(
//                currentFilter = newFilter,
//                filteredAndSortedTransactions = getFilteredAndSortedTransactions(
//                    it.allTransactions,
//                    newFilter,
//                    it.currentSortingCriteria
//                )
//            )
//        }
//    }
//
//    fun updateCurrentSortingCriteria(newSortingCriteria: String) {
//        _uiState.update {
//            it.copy(
//                currentSortingCriteria = newSortingCriteria,
//                filteredAndSortedTransactions = getFilteredAndSortedTransactions(
//                    it.allTransactions,
//                    it.currentFilter,
//                    newSortingCriteria
//                )
//            )
//        }
//    }
//
//    fun getFilteredAndSortedTransactions(
//        transactionList: List<Transaction>,
//        filter: String,
//        sort: String
//    ): List<Transaction> {
//        var result: List<Transaction> = when (filter) {
//            "Positive" -> transactionList.filter { it.amount >= 0 }
//            "Negative" -> transactionList.filter { it.amount < 0 }
//            else -> transactionList
//        }
//
//        result = when (sort) {
//            "Oldest" -> result.sortedBy { it.date }
//            "Biggest" -> result.sortedByDescending { abs(it.amount) }
//            "Smallest" -> result.sortedBy { abs(it.amount) }
//            else -> result.sortedByDescending { it.date }
//        }
//
//        return result
//    }
//
//    fun saveTransaction(transaction: Transaction?) {
//        generalViewModel.saveTransaction(transaction)
//    }
//}