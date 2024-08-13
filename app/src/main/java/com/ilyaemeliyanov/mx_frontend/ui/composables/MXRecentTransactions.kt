package com.ilyaemeliyanov.mx_frontend.ui.composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilyaemeliyanov.mx_frontend.data.transactions.Transaction
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXColors
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXTheme
import com.ilyaemeliyanov.mx_frontend.viewmodel.MXViewModelSingleton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.GregorianCalendar

@Composable
fun RecentTransactions(
    transactionList: List<Transaction>,
    modifier: Modifier = Modifier,
    showTitle: Boolean = true
) {

    val mxViewModel = MXViewModelSingleton.getInstance()

    var showAlertDialog by remember { mutableStateOf(false) }
    var selectedTransaction by remember { mutableStateOf<Transaction?>(null) }

    Column() {
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
            if (transactionList.size > 0) {
                items(transactionList) { transaction ->
                    SwipeToDeleteContainer(
                        onDelete = {
                            if(!showAlertDialog) {
                                showAlertDialog = true
                                selectedTransaction = transactionList.find { it.id == transaction.id }
                            }
                        }
                    ) {
                        MXTransaction(
                            transaction = transaction
                        )
                    }
                }
            } else {
                item {
                    Text(
                        text = "No transactions",
                        style = MaterialTheme.typography.bodyMedium
                    )
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
    }
}