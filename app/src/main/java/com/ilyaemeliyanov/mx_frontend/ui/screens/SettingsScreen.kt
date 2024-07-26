package com.ilyaemeliyanov.mx_frontend.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilyaemeliyanov.mx_frontend.ui.composables.MxRectangularButton
import com.ilyaemeliyanov.mx_frontend.ui.composables.MxSettingsButton
import com.ilyaemeliyanov.mx_frontend.ui.composables.MxTitle
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXColors
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXTheme

private const val TAG = "SettingsScreen"

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    LazyColumn (modifier = modifier) {
        item {
            MxTitle(
                title = "Settings",
                modifier = Modifier
                    .fillMaxWidth()
            ) { }
        }
        
        item {
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(8.dp))
                    .background(color = Color.Black)
                    .padding(vertical = 16.dp, horizontal = 12.dp)
            ) {
                Text(
                    text = "Welcome back,",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )
                Text(
                    text = "Mario Rossi",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MXColors.Default.activeColor
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "General", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(12.dp))
                MxSettingsButton(
                    onClick = { /*TODO*/ },
                    leftIconImageVector = Icons.Outlined.Person,
                    titleString = "Change personal info",
                    descriptionString = "Here you can change your firstname, lastname and password",
                    rightIconImageVector = Icons.Filled.KeyboardArrowRight
                )
                Spacer(modifier = Modifier.height(8.dp))
                MxSettingsButton(
                    onClick = { /*TODO*/ },
                    leftIconImageVector = Icons.Filled.KeyboardArrowDown, // TODO: set download icon
                    titleString = "Export transactions",
                    descriptionString = "Your transactions are available for download in CSV format",
                    rightIconImageVector = Icons.Filled.KeyboardArrowRight
                )
                Spacer(modifier = Modifier.height(8.dp))
                MxSettingsButton(
                    onClick = { /*TODO*/ },
                    leftIconImageVector = Icons.Outlined.ShoppingCart, // TODO: set credit card icon
                    titleString = "Change currency",
                    descriptionString = "Choose the currency to display your transactions with",
                    rightIconImageVector = Icons.Filled.KeyboardArrowRight
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Danger zone", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(12.dp))
                MxSettingsButton(
                    onClick = { /*TODO*/ },
                    leftIconImageVector = Icons.Outlined.Delete, // TODO: set delete account icon
                    titleString = "Close & delete account",
                    titleColor = Color.Red,
                    descriptionString = "Close and delete your account and all related wallets and tra...",
                    rightIconImageVector = Icons.Filled.KeyboardArrowRight
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsScreenPreview() {
    MXTheme {
        SettingsScreen(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
    }
}