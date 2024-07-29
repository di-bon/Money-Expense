package com.ilyaemeliyanov.mx_frontend.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilyaemeliyanov.mx_frontend.data.Transaction
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXColors
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXTheme
import java.util.Calendar
import java.util.GregorianCalendar

@Composable
fun RecentTransactions(
    transactionList: List<Transaction>,
    modifier: Modifier = Modifier,
    showTitle: Boolean = true
) {
    LazyColumn (modifier = modifier) {
        if (showTitle) {
            item {
                Text(
                    text = "Recent transactions",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
//            item {
//                Spacer(modifier = Modifier.height(8.dp))
//            }
        }
        if (transactionList.size > 0) {
            items(transactionList.size) { index ->
                MxTransaction(
                    transaction = transactionList[index],
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                )
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
}

@Preview(showBackground = true)
@Composable
private fun RecentTransactionsPreview() {
    MXTheme {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = MXColors.Default.bgColor)
                .padding(12.dp)
        ) {
            RecentTransactions(
                transactionList = listOf(
                    Transaction(
                        title = "Spesa",
                        amount = -24.50f,
                        date = GregorianCalendar(2024, Calendar.APRIL, 4).time,
                        description = "Groceries"
                    ),
                    Transaction(
                        title = "Gym sub",
                        amount = -69.99f,
                        date = GregorianCalendar(2024, Calendar.MARCH, 27).time,
                        description = "Health"
                    ),
                    Transaction(
                        title = "Salary",
                        amount = 3125f,
                        date = GregorianCalendar(2024, Calendar.MARCH, 7).time,
                        description = "Work at Google"
                    )
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .padding(8.dp)
            )
        }
    }
}