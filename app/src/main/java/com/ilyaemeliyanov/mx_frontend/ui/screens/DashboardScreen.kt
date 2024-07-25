package com.ilyaemeliyanov.mx_frontend.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilyaemeliyanov.mx_frontend.data.Transaction
import com.ilyaemeliyanov.mx_frontend.ui.composables.MxCard
import com.ilyaemeliyanov.mx_frontend.ui.composables.MxTitle
import com.ilyaemeliyanov.mx_frontend.ui.composables.MxTransaction
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXColors
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXTheme
import java.util.Calendar
import java.util.GregorianCalendar

private const val TAG = "DashboardScreen"

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        DashboardTopBar(
            modifier = Modifier
                .padding(vertical = 12.dp)
        )
        // Spacer(modifier = Modifier.height(20.dp))
        DashboardInfo(
            modifier = Modifier
                .padding(vertical = 12.dp)
        )
        // Spacer(modifier = Modifier.height(20.dp))

        MxCard(
            containerColor = Color.White,
            contentColor = Color.Black
        ) {
            RecentTransactions()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DashboardScreenPreview() {
    MXTheme {
        DashboardScreen(
            modifier = Modifier
                .background(color = Color(246, 246, 246))
                .padding(24.dp)
        )
    }
}

@Composable
private fun DashboardTopBar(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        MxTitle(
            title = "Dashboard",
            modifier = Modifier.fillMaxWidth()
        ) {
            CurrentWallet(onClick = {})
        }
    }
}

@Composable
private fun CurrentWallet(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        modifier = modifier,
        color = Color.Transparent
    ) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
            Text(
                text = "Wallet name",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = "Change current wallet",
                modifier = Modifier
                    .size(16.dp)
            )
        }
    }
}

@Composable
private fun DashboardInfo(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        MxCard(
            containerColor = MXColors.Default.activeColor,
            contentColor = MXColors.Default.primaryColor,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        ) {
            Text(
                text = "Balance",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "\$ 1234.00",
                style = MaterialTheme.typography.headlineMedium
            )
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            MxCard(
                containerColor = MXColors.Default.primaryColor,
                contentColor = MXColors.Default.secondaryColor,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Incomes",
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "+ \$ 234.35",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            MxCard(
                containerColor = Color.Black,
                contentColor = MXColors.Default.secondaryColor,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Expenses",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "- \$ 123.45",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DashboardInfoPreview() {
    MXTheme {
        DashboardInfo(
            modifier = Modifier
                .padding(16.dp)
        )
    }
}

@Composable
private fun RecentTransactions(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "Recent transactions",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        repeat(4) {
//            MxTransaction(modifier = Modifier.padding(8.dp))
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

