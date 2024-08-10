package com.ilyaemeliyanov.mx_frontend.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ilyaemeliyanov.mx_frontend.data.wallets.Wallet
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXInput
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXPopup
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXRectangularButton
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXTitle
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXWallet
import com.ilyaemeliyanov.mx_frontend.ui.composables.MxCircluarButton
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXColors

private const val TAG = "WalletsScreen"

@Composable
fun WalletsScreen(
    navController: NavController,
    walletList: List<Wallet>,
    modifier: Modifier = Modifier
) {

    var showPopup by remember {mutableStateOf(false)}

    Column (modifier = modifier) {
        MXTitle(
            title = "Wallets",
            modifier = Modifier
                .fillMaxWidth()
        ) {
            MxCircluarButton(
                onClick = { if (!showPopup) showPopup = true },
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


        AnimatedVisibility(
            visible = showPopup,
            enter = fadeIn(initialAlpha = 0.0f, animationSpec = tween(durationMillis = 200)),
            exit = fadeOut(animationSpec = tween(durationMillis = 200)),
            modifier = Modifier.fillMaxSize().align(Alignment.CenterHorizontally)
            ) {
                MXPopup(titleString = "Create Wallet") {
                    Spacer(modifier = Modifier.height(8.dp))
                    MXInput(
                        titleText = "Name",
                        labelText = "Enter your wallet name...",
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    MXInput(
                        titleText = "Description",
                        labelText = "Enter a short description for your wallet...",
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    MXInput(
                        titleText = "Amount",
                        labelText = "Enter the initial amount...",
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(modifier = Modifier.padding(vertical = 8.dp)) {
                        MXRectangularButton(
                            onClick = { showPopup = false },
                            containerColor = MXColors.Default.PrimaryColor,
                            contentColor = Color.White,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Cancel")
                        }
                        Spacer(modifier = Modifier.width(24.dp))
                        MXRectangularButton(
                            onClick = { showPopup = false }, // TODO
                            containerColor = MXColors.Default.ActiveColor,
                            contentColor = Color.Black,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Create")
                        }
                    }
                }
        }
    }
}



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