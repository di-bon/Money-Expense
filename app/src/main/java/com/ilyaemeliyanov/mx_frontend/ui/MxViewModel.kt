package com.ilyaemeliyanov.mx_frontend.ui

import androidx.lifecycle.ViewModel
import com.ilyaemeliyanov.mx_frontend.data.MxUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MxViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MxUiState())
    val uiState: StateFlow<MxUiState> = _uiState

}