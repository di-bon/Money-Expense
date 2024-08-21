package com.ilyaemeliyanov.mx_frontend.ui.composables

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ilyaemeliyanov.mx_frontend.data.transactions.Transaction
import com.ilyaemeliyanov.mx_frontend.viewmodel.MXViewModelSingleton
import kotlinx.coroutines.delay

@Composable
fun MXRecentTransactions(
    transactionList: List<Transaction>,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    showTitle: Boolean = true
) {

    val mxViewModel = MXViewModelSingleton.getInstance()

    var showAlertDialog by remember { mutableStateOf(false) }
    var selectedTransaction by remember { mutableStateOf<Transaction?>(null) }

//    var isLoading by remember {
//        mutableStateOf(true)
//    }
//    LaunchedEffect(mxViewModel.transactions.isNotEmpty()) {// waiting for the transactions to load from Firestore
//        if (mxViewModel.transactions.isEmpty()) {
//            delay(10000) // set max of 10000 ms
//        }
//        isLoading = false
//    }

//    Column(modifier = modifier) {
        LazyColumn (modifier = modifier) {
            if (showTitle) {
                item {
                    Text(
                        text = "Recent transactions",
                        style = MaterialTheme.typography.labelLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            if (isLoading) {
                items(20) {
                    ShimmerListItem(isLoading = isLoading, content = {})
                }
            } else {
                items(transactionList) { transaction ->
                    SwipeToDeleteContainer(
                        onDelete = {
                            if (!showAlertDialog) {
                                showAlertDialog = true
                                selectedTransaction =
                                    transactionList.find { it.id == transaction.id }
                            }
                        }
                    ) {
                        MXTransaction(
                            transaction = transaction
                        )
                    }
                }
            }
        }

        if (showAlertDialog) {
            MXAlertDialog(
                title = "Confirm deletion",
                dismissLabel = "Cancel",
                confirmLabel = "Confirm",
                onDismiss = { showAlertDialog = false },
                onConfirm = {
                    Log.d("SELECTED", selectedTransaction.toString())
                    mxViewModel.deleteTransaction(selectedTransaction)
                    showAlertDialog = false
                }) {
                Text("Are you sure you want to delete this transaction?")
            }
        }
//    }
}