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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ilyaemeliyanov.mx_frontend.AuthScreens
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXRectangularButton
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXColors
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXTheme
import com.ilyaemeliyanov.mx_frontend.ui.theme.euclidCircularA

private const val TAG = "InitialScreen"

@Composable
fun InitialScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
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
                style = TextStyle(fontFamily = euclidCircularA, fontWeight = FontWeight.SemiBold, fontSize = 64.sp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Your new journey starts here",
                style = TextStyle(
                    fontFamily = euclidCircularA,
                    fontWeight = FontWeight.Normal,
                    fontSize = 24.sp,
                    color = Color(151, 151, 151)
                )
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            MXRectangularButton(
                onClick = {
                    Log.d(TAG, "Sign up clicked")
                    navController.navigate(AuthScreens.SignUpScreen.name)
                          },
                containerColor = MXColors.Default.PrimaryColor,
                contentColor = Color.White,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .height(60.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Sign up",
                    style = MaterialTheme.typography.labelLarge
                )
            }
            MXRectangularButton(
                onClick = {
                    Log.d(TAG, "Login clicked")
                    navController.navigate(AuthScreens.LoginScreen.name) },
                containerColor = MXColors.Default.ActiveColor,
                contentColor = Color.Black,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .height(60.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.labelLarge
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
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = MXColors.Default.BgColor)
                .padding(32.dp)
        )
    }
}