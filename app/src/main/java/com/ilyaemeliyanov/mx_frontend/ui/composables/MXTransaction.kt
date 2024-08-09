package com.ilyaemeliyanov.mx_frontend.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilyaemeliyanov.mx_frontend.data.transactions.Transaction
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXColors
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXShapes
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXTheme
import com.ilyaemeliyanov.mx_frontend.utils.StringFormatter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.Locale

@Composable
fun MXTransaction(
    transaction: Transaction,
    modifier: Modifier = Modifier,
    showBottomDivider: Boolean = true
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
                imageVector = if (isExpanded) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowRight, // TODO: change icons
                contentDescription = null,
                modifier = Modifier
                    // .padding(4.dp)
                    .width(32.dp)
                // .rotate(-90f)
            )
            Spacer(modifier = Modifier.width(4.dp))

            Box(modifier = Modifier.width(50.dp).height(50.dp).padding(8.dp).clip(MXShapes.medium).background(Color(246, 246, 246)), contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = if (transaction.amount < 0) Icons.Filled.ArrowDownward else Icons.Filled.ArrowUpward,
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
                text = StringFormatter.getFormattedAmount(transaction.amount),
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
//                     start = (32 + 4).dp
                )
            ) {
                Text(
                    text = transaction.description ?: "No description",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        if (showBottomDivider) {
            Divider(
                modifier = Modifier
                    .padding(vertical = 8.dp),
                thickness = 1.dp,
                // TODO: add this color to Color.kt
                color = Color(232, 232, 232) // E8E8E8
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