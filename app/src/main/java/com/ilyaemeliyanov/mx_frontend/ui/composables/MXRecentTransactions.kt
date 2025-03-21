package com.ilyaemeliyanov.mx_frontend.ui.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilyaemeliyanov.mx_frontend.data.transactions.Transaction
import com.ilyaemeliyanov.mx_frontend.data.user.Currency
import com.ilyaemeliyanov.mx_frontend.ui.theme.euclidCircularA
import com.ilyaemeliyanov.mx_frontend.viewmodel.MXViewModel

@Composable
fun MXRecentTransactions(
    mxViewModel: MXViewModel,
    transactionList: List<Transaction>,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    showTitle: Boolean = true
) {
//    var showAlertDialog by remember { mutableStateOf(false) }
    var selectedTransaction by remember { mutableStateOf<Transaction?>(null) }

    LazyColumn (modifier = modifier) {
        if (showTitle) {
            item {
                Text(
                    text = "Recent transactions",
                    style = MaterialTheme.typography.labelLarge
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        if (isLoading) {
            items(20) {
                ShimmerListItem(isLoading = isLoading, content = {})
            }
        } else {
            if (transactionList.isNotEmpty()) {
                items(transactionList, key = { t -> t.id }) { transaction ->
                    SwipeToDeleteContainer(
                        onDelete = {
                            selectedTransaction = transactionList.find { it.id == transaction.id }
                            mxViewModel.deleteTransaction(selectedTransaction)
                        }
                    ) {
                        MXTransaction(
                            transaction = transaction,
                            currency = mxViewModel.user?.currency ?: Currency.US_DOLLAR,
                        )
                    }
                }
            } else {
                item {
                    MXCard (
                        contentPadding = 0.dp
                    ) {
                        Text(
                            text = "You don't have any transactions.",
                            style = TextStyle(
                                fontFamily = euclidCircularA,
                                fontWeight = FontWeight.Light,
                                fontSize = 18.sp
                            )
                        )
                    }
                }
            }
        }
    }
}