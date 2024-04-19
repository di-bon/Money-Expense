package com.ilyaemeliyanov.mx_frontend.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilyaemeliyanov.mx_frontend.ui.theme.MxfrontendTheme

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    Text(text = "Login screen")
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    MxfrontendTheme {
        LoginScreen(modifier = Modifier.padding(16.dp))
    }
}