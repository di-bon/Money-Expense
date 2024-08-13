package com.ilyaemeliyanov.mx_frontend.ui.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ilyaemeliyanov.mx_frontend.data.transactions.Transaction
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXAlertDialog
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXCard
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXDatePicker
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXDropdownMenu
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXInput
import com.ilyaemeliyanov.mx_frontend.ui.composables.MxCircluarButton
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXTitle
import com.ilyaemeliyanov.mx_frontend.ui.composables.RecentTransactions
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXColors
import com.ilyaemeliyanov.mx_frontend.utils.StringFormatter.getDateFromString
import com.ilyaemeliyanov.mx_frontend.utils.StringFormatter.getStringFromDate
import com.ilyaemeliyanov.mx_frontend.viewmodel.MXViewModelSingleton
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale

private const val TAG = "TransactionsScreen"

@Composable
fun TransactionsScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {

    val mxViewModel = remember { MXViewModelSingleton.getInstance() }
    val transactions = mxViewModel.transactions

    var showContextDialog by remember { mutableStateOf(false) }
    var label by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var date: Date? by remember { mutableStateOf(null) }

    val walletNames = mxViewModel.wallets?.map { it.name }
    var walletName by remember { mutableStateOf("Select wallet...") }

    Column (
        modifier = modifier.fillMaxHeight()
    ) {
        MXTitle(title = "Transactions", modifier = Modifier.fillMaxWidth()) {
            MxCircluarButton(
                onClick = { if(!showContextDialog) showContextDialog = true },
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

        if (showContextDialog) {
            MXAlertDialog(
                title = "Create Transaction",
                dismissLabel = "Cancel",
                confirmLabel = "Create",
                onDismiss = { showContextDialog = false },
                onConfirm = {
                    val wallet = mxViewModel.wallets.find { it.name == walletName }
                    // TODO: validate input
                    if (wallet != null) {
                        val transaction = Transaction(
                            id = "",
                            label = label,
                            description = description,
                            amount = amount.toFloat(),
                            date = date ?: Date(),
                            wallet = wallet,
                        )
                        mxViewModel.saveTransaction(transaction)
                    }
                    showContextDialog = false
                }) {
                Spacer(modifier = Modifier.height(8.dp))
                MXInput(
                    titleText = "Label",
                    labelText = "Enter transaction label...",
                    text = label,
                    onTextChange = { label = it },
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                MXInput(
                    titleText = "Description",
                    labelText = "Enter a short transaction description...",
                    text = description,
                    onTextChange = { value -> description = value },
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                MXInput(
                    titleText = "Amount",
                    labelText = "Enter transaction value...",
                    text = amount,
                    onTextChange = { value -> amount = value },
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Column() {
                    Text("Wallet")
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .border(
                            1.dp,
                            brush = SolidColor(Color.Gray),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 16.dp)
                    ) {
                        MXDropdownMenu(items = walletNames ?: emptyList(), selectedItem = walletName) {
                            walletName = it
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                Column() {
                    Text("Date")
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(modifier = Modifier
                        .fillMaxWidth()
                    ) {
                        if (date == null) {
                           MXDatePicker(date = "Select date") {
                               Log.d("Transaction Date", it.toString())
                               date = getDateFromString(it) ?: Date()
                           } // Convert info from string to date
                        } else {
                            MXDatePicker(
                                getStringFromDate(date ?: Date())
                            ) {date = getDateFromString(it) ?: Date() }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }


        Spacer(modifier = Modifier.height(8.dp))

        Row (
            modifier = Modifier.fillMaxWidth()
        ) {
            // TODO: replace with MXDropdownMenu
            MxCircluarButton(
                onClick = { /*TODO*/ },
                containerColor = MXColors.Default.ActiveColor,
                contentColor = Color.Black,
                modifier = Modifier.weight(1f)
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(text = "Filter by")
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(imageVector = Icons.Filled.KeyboardArrowDown, contentDescription = "Filter By")
                }
            }
            Spacer(modifier = Modifier.width(24.dp))
            // TODO: replace with MXDropdownMenu
            MxCircluarButton(
                onClick = { /*TODO*/ },
                containerColor = MXColors.Default.ActiveColor,
                contentColor = Color.Black,
                modifier = Modifier.weight(1f)
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(text = "Sort by")
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(imageVector = Icons.Filled.KeyboardArrowDown, contentDescription = "Filter By")
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(8.dp))
                .background(color = Color.Black)
        ) {
            Text(
                text = "Sum: ",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        MXCard(
            containerColor = Color.White
        ) {
            RecentTransactions(
                showTitle = false,
                transactionList = transactions,
            )
        }

    }
}

//@Preview(showBackground = true)
//@Composable
//private fun TransactionsScreenPreview() {
//    MXTheme {
//        TransactionsScreen(
//            modifier = Modifier
//                .fillMaxWidth()
//                .fillMaxHeight()
//                .background(color = MXColors.Default.BgColor)
//                .padding(16.dp)
//        )
//    }
//}