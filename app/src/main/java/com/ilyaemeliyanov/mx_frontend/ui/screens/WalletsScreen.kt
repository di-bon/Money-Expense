package com.ilyaemeliyanov.mx_frontend.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilyaemeliyanov.mx_frontend.data.Wallet
import com.ilyaemeliyanov.mx_frontend.ui.composables.MxCircluarButton
import com.ilyaemeliyanov.mx_frontend.ui.composables.MxTitle
import com.ilyaemeliyanov.mx_frontend.ui.composables.MxWallet
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXTheme

private const val TAG = "WalletsScreen"

@Composable
fun WalletsScreen(modifier: Modifier = Modifier) {
    Column (modifier = modifier) {
        MxTitle(
            title = "Wallets",
            modifier = Modifier
                .fillMaxWidth()
        ) {
            MxCircluarButton(
                onClick = {},
                modifier = Modifier
                    .padding(8.dp)
                    .size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn (
            modifier = Modifier
                .fillMaxHeight()
        ) {
            item {
                MxWallet(
                    wallet = Wallet(
                        name = "PayPal",
                        totalAmount = 1234.00f,
                        description = "Wallet for tracing transactions from and to my personal paypal account"
                    ),
                    cardColor = Color(red = 105, green = 247, blue = 179),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(168.dp)
                        .padding(vertical = 8.dp)
                )
            }
            item {
                MxWallet(
                    wallet = Wallet(
                        name = "Credit Card",
                        totalAmount = 130456.95f,
                        description = "Credit card related to my swiss bank account"
                    ),
                    cardColor = Color(red = 236, green = 247, blue = 105),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(168.dp)
                        .padding(vertical = 8.dp)
                )
            }
            item {
                MxWallet(
                    wallet = Wallet(
                        name = "Revolut",
                        totalAmount = 2.95f,
                        description = "Revolut gold account used for shopping expenses only"
                    ),
                    cardColor = Color(red = 243, green = 184, blue = 116),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(168.dp)
                        .padding(vertical = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WalletsScreenPreview() {
    MXTheme {
        WalletsScreen(modifier = Modifier
            .fillMaxSize()
            .padding(32.dp))
    }
}