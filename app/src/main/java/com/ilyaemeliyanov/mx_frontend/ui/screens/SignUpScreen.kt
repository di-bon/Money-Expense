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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXInput
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXRectangularButton
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXTheme

private const val TAG = "RegisterScreen"

@Composable
fun RegisterScreen(
    onSignUpClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Sign up", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(24.dp))
        Column {
            MXInput(
                titleText = "First name",
                labelText = "Enter your first name...",
                text = firstName,
                onTextChange = { value -> firstName = value },
                modifier = Modifier.padding(vertical = 8.dp)
            )
            MXInput(
                titleText = "Last name",
                labelText = "Enter your last name...",
                text = lastName,
                onTextChange = { value -> lastName = value },
                modifier = Modifier.padding(vertical = 8.dp)
            )
            MXInput(
                titleText = "Email",
                labelText = "Enter your email...",
                text = email,
                onTextChange = { value -> email = value },
                modifier = Modifier.padding(vertical = 8.dp)
            )
            MXInput(
                titleText = "Password",
                labelText = "Enter your password...",
                text = password,
                onTextChange = { value -> password = value },
                modifier = Modifier.padding(vertical = 8.dp)
            )
            MXInput(
                titleText = "Confirm password",
                labelText = "Enter your password...",
                text = confirmPassword,
                onTextChange = { value -> confirmPassword = value },
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        MXRectangularButton(
            onClick = onSignUpClick,
            containerColor = Color.Black,
            contentColor = Color.White,
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Sign up",
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview() {
    MXTheme {
        RegisterScreen({}, modifier = Modifier
            .background(color = Color(246, 246, 246))
            .padding(32.dp)
            .fillMaxWidth()
            .fillMaxHeight()
        )
    }
}