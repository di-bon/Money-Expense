package com.ilyaemeliyanov.mx_frontend.ui

import com.ilyaemeliyanov.mx_frontend.data.transactions.Transaction
import com.ilyaemeliyanov.mx_frontend.data.user.User
import com.ilyaemeliyanov.mx_frontend.data.wallets.Wallet
import com.ilyaemeliyanov.mx_frontend.viewmodel.MXViewModel
import com.ilyaemeliyanov.mx_frontend.viewmodel.MXViewModelSingleton

data class UiState (
    val allTransactions: List<Transaction> = listOf(),
    var filteredAndSortedTransactions: List<Transaction> = listOf(),

    val walletNames: List<String> = listOf(),
    val currentWalletName: String? = null,

    val currentFilter: String = "None",
    val currentSortingCriteria: String = "Newest",

    val sum: Float = 0f,

    val user: User? = null,
    val wallets: List<Wallet> = listOf(),
)