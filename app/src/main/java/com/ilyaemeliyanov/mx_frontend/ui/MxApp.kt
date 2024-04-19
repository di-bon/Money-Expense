package com.ilyaemeliyanov.mx_frontend.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ilyaemeliyanov.mx_frontend.ui.screens.DashboardScreen
import com.ilyaemeliyanov.mx_frontend.ui.theme.MxfrontendTheme

@Composable
fun MxApp (
    modifier: Modifier = Modifier
) {
    val viewModel: MxViewModel = viewModel()
    val mxUiState = viewModel.uiState.collectAsState().value

    DashboardScreen(modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun MxAppPreview() {
    MxfrontendTheme {
        MxApp(modifier = Modifier.padding(16.dp))
    }
}