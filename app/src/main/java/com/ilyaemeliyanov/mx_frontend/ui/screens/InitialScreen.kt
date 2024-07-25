package com.ilyaemeliyanov.mx_frontend.ui.screens

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
import androidx.compose.ui.unit.sp
import com.ilyaemeliyanov.mx_frontend.ui.composables.MxRectangularButton
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXColors
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXTheme

private const val TAG = "InitialScreen"

@Composable
fun InitialScreen(
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Money Expense",
                lineHeight = 68.sp,
                style = MaterialTheme.typography.displayLarge
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Your new journey starts here",
                style = MaterialTheme.typography.titleLarge
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            MxRectangularButton(
                onClick = onSignUpClick,
                containerColor = Color.Black,
                contentColor = Color.White,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .height(60.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Sign up",
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            MxRectangularButton(
                onClick = onLoginClick,
                containerColor = MXColors.Default.activeColor,
                contentColor = Color.Black,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .height(60.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InitialScreenPreview() {
    MXTheme {
        InitialScreen(
            onLoginClick = {},
            onSignUpClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(24.dp)
        )
    }
}