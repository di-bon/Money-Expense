package com.ilyaemeliyanov.mx_frontend.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ilyaemeliyanov.mx_frontend.data.Wallet
import com.ilyaemeliyanov.mx_frontend.ui.composables.MxCircluarButton
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXTitle
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXWallet
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXTheme

private const val TAG = "WalletsScreen"

@Composable
fun WalletsScreen(
    navController: NavController,
    walletList: List<Wallet>,
    modifier: Modifier = Modifier
) {
    Column (modifier = modifier) {
        MXTitle(
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
            items(walletList.size) { index ->
                MXWallet(
                    wallet = walletList[index],
                    cardColor = when (index % 3) {
                        0 -> Color(red = 105, green = 247, blue = 179)
                        1 -> Color(red = 236, green = 247, blue = 105)
                        2 -> Color(red = 243, green = 184, blue = 116)
                        else -> Color(red = 105, green = 247, blue = 179)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(168.dp)
                        .padding(vertical = 8.dp)
                )
            }
        }
    }
}
//
//@Preview(showBackground = true)
//@Composable
//private fun WalletsScreenPreview() {
//    MXTheme {
//        WalletsScreen(
//            walletList = listOf(
//                Wallet(
//                    name = "PayPal",
//                    totalAmount = 1234.00f,
//                    description = "Wallet for tracing transactions from and to my personal paypal account"
//                ),
//                Wallet(
//                    name = "Credit Card",
//                    totalAmount = 130456.95f,
//                    description = "Credit card related to my swiss bank account"
//                ),
//                Wallet(
//                    name = "Revolut",
//                    totalAmount = 2.95f,
//                    description = "Revolut gold account used for shopping expenses only"
//                )
//            ),
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(32.dp)
//        )
//    }
//}