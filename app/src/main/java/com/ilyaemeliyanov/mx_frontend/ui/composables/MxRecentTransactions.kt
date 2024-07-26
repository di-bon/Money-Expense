package com.ilyaemeliyanov.mx_frontend.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXTheme
import java.util.Calendar
import java.util.GregorianCalendar

@Composable
fun RecentTransactions(
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
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        repeat(4) {
            item {
                MxTransaction(
                    transaction = Transaction(
                        title = "Spesa",
                        amount = -24.50f,
                        date = GregorianCalendar(2024, Calendar.APRIL, 4).time,
                        description = null
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RecentTransactionsPreview() {
    MXTheme {
        RecentTransactions(
            modifier = Modifier
                .background(color = Color(246, 246, 246))
                .padding(8.dp)
        )
    }
}