package com.ilyaemeliyanov.mx_frontend.ui.screens

import android.util.Log
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXInput
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXRectangularButton
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXColors
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXTheme
import com.ilyaemeliyanov.mx_frontend.ui.theme.euclidCircularA
import com.ilyaemeliyanov.mx_frontend.viewmodel.MXAuthViewModel

private const val TAG = "LoginScreen"

@Composable
fun LoginScreen(
    navController: NavController,
    mxAuthViewModel: MXAuthViewModel,
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Login", style = TextStyle(fontFamily = euclidCircularA, fontWeight = FontWeight.SemiBold, fontSize = 64.sp))
        Spacer(modifier = Modifier.height(24.dp))
        Column {
            MXInput(
                titleText = "Email",
                labelText = "Enter your email...",
                text = email,
                onTextChange = { value -> email = value }, // TODO: Check valid email
                modifier = Modifier.padding(vertical = 8.dp)
            )
            MXInput(
                titleText = "Password",
                labelText = "Enter your password...",
                text = password,
                onTextChange = { value -> password = value },
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        MXRectangularButton(
            onClick = {
                mxAuthViewModel.logIn(email = email, password = password) {
                    res, error ->
                    if (res) {
                        Log.d(TAG, "Login successful")
                    } else {
                        Log.d(TAG, "Login failed: $error")
                    }
                }
            },
            containerColor = MXColors.Default.ActiveColor,
            contentColor = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(text = "Log in", style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    MXTheme {
//        LoginScreen(
//            onLoginClick = {},
//            modifier = Modifier
//                .background(color = Color(246, 246, 246))
//                .padding(32.dp)
//                .fillMaxWidth()
//                .fillMaxHeight()
//        )
    }
}