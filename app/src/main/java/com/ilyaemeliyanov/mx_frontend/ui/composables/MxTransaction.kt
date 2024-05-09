package com.ilyaemeliyanov.mx_frontend.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilyaemeliyanov.mx_frontend.data.Transaction
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXTheme
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.Locale

@Composable
fun MxTransaction(
    transaction: Transaction,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column (
        modifier = modifier
            .clickable {
                isExpanded = !isExpanded
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = if (transaction.amount < 0) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowUp,
                contentDescription = null,
                modifier = Modifier
                    // .padding(4.dp)
                    .width(32.dp)
                // .rotate(-90f)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(text = transaction.title, style = MaterialTheme.typography.bodyMedium)

                val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formatttedDate = formatter.format(transaction.date)

                Text(
                    text = formatttedDate.toString(),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Text(
                text = "%.2f".format(transaction.amount),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
            )
        }
        if (isExpanded) {
            Row (modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 8.dp,
                    // TODO: set correct dimension of description Text
                    // start = (32 + 4).dp
                )
            ) {
                Text(
                    text = transaction.description ?: "No description",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MxTransactionPreview() {
    MXTheme {
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