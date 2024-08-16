package com.ilyaemeliyanov.mx_frontend.ui.screens

import android.util.Log
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXTheme
import com.ilyaemeliyanov.mx_frontend.utils.StringFormatter.getDateFromString
import com.ilyaemeliyanov.mx_frontend.utils.StringFormatter.getStringFromDate
import com.ilyaemeliyanov.mx_frontend.viewmodel.MXViewModelSingleton
import java.util.Date
import kotlin.math.abs

private const val TAG = "TransactionsScreen"

@Composable
fun TransactionsScreen(
//    navController: NavController,
    modifier: Modifier = Modifier
) {

    val mxViewModel = remember { MXViewModelSingleton.getInstance() }
    val allTransactions = mxViewModel.transactions

    var showContextDialog by remember { mutableStateOf(false) }
    var label by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var date: Date? by remember { mutableStateOf(null) }

    val walletNames = mxViewModel.wallets?.map { it.name }
    var walletName by remember { mutableStateOf("Select wallet...") }

    var currentFilter by remember { mutableStateOf("None") }
    var currentSortCriteria by remember { mutableStateOf("Newest") }

    var desiredTransactions = getDesiredTransactions(allTransactions, currentFilter, currentSortCriteria)

    var totalSum by remember {
        mutableStateOf(0f)
    }

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
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            // TODO: replace with MXDropdownMenu

            MXCard(
                containerColor = MXColors.Default.ActiveColor,
                contentColor = Color.Black,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
//                Row (
//                    verticalAlignment = Alignment.CenterVertically,
//                    modifier = Modifier
//                        .padding(horizontal = 16.dp, vertical = 8.dp)
//                ) {
//                    Text(text = "Filter by")
//                    Spacer(modifier = Modifier.weight(1f))
//                    Icon(imageVector = Icons.Filled.KeyboardArrowDown, contentDescription = "Filter By")
//                }
                MXDropdownMenu(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = "Filter by",
                    items = listOf(
                        "None",
                        "Positive",
                        "Negative"
                    ),
                    selectedItem = currentFilter
                ) {
                    currentFilter = it
                }
            }
            Spacer(modifier = Modifier.width(24.dp))
            // TODO: replace with MXDropdownMenu

//            MxCircluarButton(
//                onClick = { /*TODO*/ },
//                containerColor = MXColors.Default.ActiveColor,
//                contentColor = Color.Black,
//                modifier = Modifier.weight(1f)
//            ) {
//                Row (
//                    verticalAlignment = Alignment.CenterVertically,
//                    modifier = Modifier
//                        .padding(horizontal = 16.dp, vertical = 8.dp)
//                ) {
//                    Text(text = "Sort by")
//                    Spacer(modifier = Modifier.weight(1f))
//                    Icon(imageVector = Icons.Filled.KeyboardArrowDown, contentDescription = "Filter By")
//                }
//            }

            MXCard (
                containerColor = MXColors.Default.ActiveColor,
                contentColor = Color.Black,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                MXDropdownMenu(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = "Sort by",
                    items = listOf(
                        "Newest",
                        "Oldest",
                        "Biggest",
                        "Smallest"
                    ),
                    selectedItem = currentSortCriteria
                ) {
                    currentSortCriteria = it
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
                text = "Sum: ${desiredTransactions.fold(0.0) { sum, transaction -> sum + transaction.amount }.toFloat()}",
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
                transactionList = getDesiredTransactions(allTransactions, currentFilter, currentSortCriteria)
//                transactionList = transactions,
//                transactionList = listOf(
//                        Transaction(
//                        id = "",
//                        label = "test",
//                        description = "boh",
//                        amount = 100f,
//                        date = GregorianCalendar(2024, Calendar.APRIL, 4).time,
//                        wallet = Wallet(
//                            id = "",
//                            name = "name",
//                            amount = 200f,
//                            description = "description",
//                            ref = null
//                        )
//                    )
//                )
            )
        }
    }
}

private fun getDesiredTransactions(transactionList: List<Transaction>, filter: String, sort: String): List<Transaction> {
    var result: List<Transaction> = when (filter) {
        "Positive" -> transactionList.filter { it.amount >= 0 }
        "Negative" -> transactionList.filter { it.amount < 0 }
        else -> transactionList
    }

   result = when (sort) {
        "Oldest" -> result.sortedBy { it.date }
        "Biggest" -> result.sortedByDescending { abs(it.amount) }
        "Smallest" -> result.sortedBy { abs(it.amount) }
        else -> result.sortedByDescending { it.date }
    }

    return result
}

@Preview(showBackground = true)
@Composable
private fun TransactionsScreenPreview() {
    MXTheme {
        TransactionsScreen(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = MXColors.Default.BgColor)
                .padding(16.dp)
        )
    }
}