package com.ilyaemeliyanov.mx_frontend.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXTheme

@Composable
fun RegisterScreen(
    onSignUpClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Sign up", style = MaterialTheme.typography.displayLarge)
        Column {
            MxInput(
                titleText = "First name",
                labelText = "Enter your first name...",
                modifier = Modifier.padding(vertical = 8.dp)
            )
            MxInput(
                titleText = "Last name",
                labelText = "Enter your last name...",
                modifier = Modifier.padding(vertical = 8.dp)
            )
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
            MxInput(
                titleText = "Confirm password",
                labelText = "Enter your password...",
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        MxRectangularButton(
            onClick = onSignUpClick,
            containerColor = Color.Black,
            contentColor = Color.White,
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Sign up",
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview() {
    MXTheme {
        RegisterScreen({}, modifier = Modifier
            .padding(32.dp)
            .fillMaxWidth()
            .fillMaxHeight()
        )
    }
}