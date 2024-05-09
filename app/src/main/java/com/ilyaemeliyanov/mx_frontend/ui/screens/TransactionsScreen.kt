package com.ilyaemeliyanov.mx_frontend.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXTheme

@Composable
fun TransactionsScreen(modifier: Modifier = Modifier) {
    Text(text = "Transactions screen")
}

@Preview(showBackground = true)
@Composable
private fun TransactionsScreenPreview() {
    MXTheme {
        TransactionsScreen(modifier = Modifier.padding(16.dp))
    }
}