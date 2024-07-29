package com.ilyaemeliyanov.mx_frontend.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilyaemeliyanov.mx_frontend.ui.composables.MxInput
import com.ilyaemeliyanov.mx_frontend.ui.composables.MxRectangularButton
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXColors
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXTheme

private const val TAG = "LoginScreen"

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit,
    titleString: String,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = titleString, style = MaterialTheme.typography.displayLarge)
        Spacer(modifier = Modifier.height(24.dp))
        Column {
            MxInput(
                titleText = "Email",
                labelText = "Enter your email...",
                modifier = Modifier.padding(vertical = 8.dp)
            )
            MxInput(
                titleText = "Password",
                labelText = "Enter your password...",
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        MxRectangularButton(
            onClick = onLoginClick,
            containerColor = MXColors.Default.activeColor,
            contentColor = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(text = "Log in", style = MaterialTheme.typography.headlineSmall)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    MXTheme {
        LoginScreen(
            onLoginClick = {},
            titleString = "Log in",
            modifier = Modifier
                .background(color = Color(246, 246, 246))
                .padding(32.dp)
                .fillMaxWidth()
                .fillMaxHeight()
        )
    }
}