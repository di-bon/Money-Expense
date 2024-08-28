package com.ilyaemeliyanov.mx_frontend.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ilyaemeliyanov.mx_frontend.ui.UiState
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXInput
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXAlertDialog
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXTitle
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXWallet
import com.ilyaemeliyanov.mx_frontend.ui.composables.MxCircluarButton
import com.ilyaemeliyanov.mx_frontend.viewmodel.MXViewModel
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

private const val TAG = "WalletsScreen"

@Composable
fun WalletsScreen(
    mxViewModel: MXViewModel,
    uiState: UiState
) {

    val wallets = mxViewModel.wallets

    var showContextDialog by remember {mutableStateOf(false)}

    var isWalletNameValid by remember { mutableStateOf(true) }
    var isDescriptionValid by remember { mutableStateOf(true) }
    var isAmountValid by remember { mutableStateOf(true) }

    Column (modifier = Modifier
        .padding(32.dp)) {
        MXTitle(
            title = "Wallets",
            modifier = Modifier
                .fillMaxWidth()
        ) {
            MxCircluarButton(
                onClick = { if (!showContextDialog) showContextDialog = true },
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
        Spacer(modifier = Modifier.height(32.dp))

        LazyColumn (
            modifier = Modifier
                .fillMaxHeight()
        ) {
            items(wallets.size) {i ->
                MXWallet(
                    wallet = wallets[i],
                    cardColor = when (i % 3) {
                        0 -> Color(red = 105, green = 247, blue = 179)
                        1 -> Color(red = 236, green = 247, blue = 105)
                        2 -> Color(red = 243, green = 184, blue = 116)
                        else -> Color(red = 105, green = 247, blue = 179)
                    },
                    mxViewModel = mxViewModel,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(168.dp)
                        .padding(vertical = 8.dp)
                )
            }
        }

        if (showContextDialog) {
            MXAlertDialog(
                "Create Wallet",
                confirmLabel = "Create",
                dismissLabel = "Cancel",
                onDismiss = { showContextDialog = false },
                onConfirm = {
                    mxViewModel.walletName = mxViewModel.walletName.trim()
                    isWalletNameValid = mxViewModel.validateContent(mxViewModel.walletName)
                    mxViewModel.walletDescription = mxViewModel.walletDescription.trim()
                    isDescriptionValid = mxViewModel.validateContent(mxViewModel.walletDescription)
                    isAmountValid = try {
                        mxViewModel.walletAmount.toFloat()
                        true
                    } catch (e: Exception) {
                        false
                    }
//                    isAmountValid = try {
//                        val decimalFormat = DecimalFormat("#.00", DecimalFormatSymbols().apply {
//                            groupingSeparator = ','
//                            decimalSeparator = '.'
//                        })
//                        decimalFormat.parse(mxViewModel.walletAmount)?.toFloat()
//                        true
//                    } catch (e: Exception) {
//                        false
//                    }
//                    isAmountValid = try {
//                        val formattedAmount = "%.2f".format(mxViewModel.walletAmount.toFloat(), Locale.US)
//                        Log.d(TAG, "formattedAmount: $formattedAmount")
//                        formattedAmount.toFloat()
//                        true
//                    } catch (e: Exception) {
//                        Log.d(TAG, "Cannot parse ${mxViewModel.walletAmount} to float")
//                        false
//                    }

                    if (isWalletNameValid && isDescriptionValid && isAmountValid) {
                        mxViewModel.createAndSaveWallet()
                        showContextDialog = false
                        mxViewModel.walletName = ""
                        mxViewModel.walletDescription = ""
                        mxViewModel.walletAmount = ""
                    }
                }
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                MXInput(
                    titleText = "Name",
                    labelText = "Enter your wallet name...",
                    text = mxViewModel.walletName,
                    onTextChange = {
                        mxViewModel.walletName = it
                        isWalletNameValid = true
                                   },
                    modifier = Modifier.padding(vertical = 8.dp),
                    isError = !isWalletNameValid,
                    errorMessage = "Enter a valid wallet name"
                )
                MXInput(
                    titleText = "Description",
                    labelText = "Enter a short description for your wallet...",
                    text = mxViewModel.walletDescription,
                    onTextChange = {
                        mxViewModel.walletDescription = it
                        isDescriptionValid = true
                                   },
                    modifier = Modifier.padding(vertical = 8.dp),
                    isError = !isDescriptionValid,
                    errorMessage = "Enter a valid wallet description"
                )
                MXInput(
                    titleText = "Amount",
                    labelText = "Enter the initial amount...",
                    text = mxViewModel.walletAmount,
                    onTextChange = {
                        mxViewModel.walletAmount = it
                        isAmountValid = true
                                   },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.padding(vertical = 8.dp),
                    isError = !isAmountValid,
                    errorMessage = "Enter a valid wallet amount (use . for decimal values)"
                )
                Spacer(modifier = Modifier.weight(1f))
//                Row(modifier = Modifier.padding(vertical = 8.dp)) {
//
//                    Spacer(modifier = Modifier.width(24.dp))
//
//                }
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