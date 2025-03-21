package com.ilyaemeliyanov.mx_frontend.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Divider
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ilyaemeliyanov.mx_frontend.data.transactions.Transaction
import com.ilyaemeliyanov.mx_frontend.data.user.Currency
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXColors
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXShapes
import com.ilyaemeliyanov.mx_frontend.utils.StringFormatter
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun MXTransaction(
    transaction: Transaction,
    currency: Currency,
    modifier: Modifier = Modifier,
    showBottomDivider: Boolean = true
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column (
        modifier = modifier
            .background(Color.White)
            .fillMaxSize()
            .clip(MXShapes.medium)
            .clickable { isExpanded = !isExpanded }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(4.dp)
        ) {
            Icon(
                imageVector = if (isExpanded) Icons.Filled.KeyboardArrowDown else Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier
                    .width(32.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))

            Box(modifier = Modifier.width(50.dp).height(50.dp).padding(8.dp).clip(MXShapes.medium).background(Color(246, 246, 246)), contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = if (transaction.amount < 0) Icons.Filled.ArrowUpward else Icons.Filled.ArrowDownward,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(text = transaction.label, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)

                val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedDate = formatter.format(transaction.date)

                Text(
                    text = formattedDate.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MXColors.Default.SecondaryColor
                )
            }
            Text(
                text =
                if (transaction.amount >= 0f)
                    "+ ${currency.symbol} ${StringFormatter.getFormattedAmount(transaction.amount)}"
                else
                    "- ${currency.symbol} ${StringFormatter.getFormattedAmount(transaction.amount)}",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
            )
        }
        if (isExpanded) {
            Row (modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 8.dp,
                    start = 16.dp,
                )
            ) {
                Text(
                    text = "Wallet: ${transaction.wallet.name}${if (transaction.description != "") "\n\n${transaction.description}" else ""}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        if (showBottomDivider) {
            Divider(
                modifier = Modifier
                    .padding(vertical = 8.dp),
                thickness = 1.dp,
                color = Color(232, 232, 232) // hex color: E8E8E8
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun MXTransactionPreview() {
//    MXTheme {
//        Column (
//            modifier = Modifier
//                .fillMaxWidth()
//                .fillMaxHeight()
//                .background(color = MXColors.Default.BgColor)
//                .padding(12.dp)
//        ) {
//            MXTransaction(
//                transaction = Transaction(
//                    title = "Spesa",
//                    amount = -24.50f,
//                    date = GregorianCalendar(2024, Calendar.APRIL, 4).time,
//                    description = null
//                ),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(4.dp)
//            )
//        }
//    }
//}