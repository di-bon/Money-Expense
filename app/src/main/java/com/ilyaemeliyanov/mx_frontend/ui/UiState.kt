package com.ilyaemeliyanov.mx_frontend.ui

data class UiState (
    val currentWallet: String? = null,
    val currentFilter: String = "None",
    val currentSortingCriteria: String = "Newest"
)