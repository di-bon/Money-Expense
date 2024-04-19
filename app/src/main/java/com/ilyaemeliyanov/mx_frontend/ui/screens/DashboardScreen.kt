package com.ilyaemeliyanov.mx_frontend.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilyaemeliyanov.mx_frontend.ui.theme.MxfrontendTheme

@Composable
fun DashboardScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        DashboardTopBar()
        Spacer(modifier = Modifier.height(20.dp))
        DashboardInfo()
        Spacer(modifier = Modifier.height(20.dp))
        RecentTransactions()
    }
}

@Preview(showBackground = true)
@Composable
private fun DashboardScreenPreview() {
    MxfrontendTheme {
        DashboardScreen(modifier = Modifier.padding(16.dp))
    }
}

@Composable
private fun DashboardTopBar(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = "Dashboard",
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.weight(1f))
        CurrentWallet()
    }
}

@Composable
private fun CurrentWallet(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = "Wallet name",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.width(4.dp))
        // TODO: consider replacing this button with a custom image,
        //  because 'Button' doesn't shrink to the size of 'Icon'
        Button(
            onClick = { /* TODO */ },
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            ),
            modifier = Modifier.wrapContentSize(Alignment.Center)
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = "Change current wallet",
                tint = Color.White,
                modifier = Modifier
                    .size(16.dp)
            )
        }
    }
}

@Composable
private fun DashboardInfo(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        DashboardBox(
            title = "Balance",
//            containerColor = Color.Yellow,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        )
        Row(modifier = Modifier.fillMaxWidth()) {
            DashboardBox(
                title = "Incomes",
//                containerColor = Color.Black,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(12.dp))
            DashboardBox(
                title = "Expenses",
//                containerColor = Color.Black,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun DashboardBox(
    title: String,
//    containerColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "\$ 1234.00",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DashboardBoxPreview() {
    MxfrontendTheme {
        DashboardBox(
            title = "Balance",
//            containerColor = Color.Yellow,
            modifier = Modifier
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
        repeat(4) {
            Transaction(modifier = Modifier.padding(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RecentTransactionsPreview() {
    MxfrontendTheme {
        RecentTransactions(modifier = Modifier.padding(8.dp))
    }
}

@Composable
private fun Transaction(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Filled.KeyboardArrowUp,
            contentDescription = null,
            modifier = Modifier
                .padding(4.dp)
                .width(32.dp)
                // .rotate(-90f)
        )
        Column (
            modifier = Modifier
                .weight(1f)
        ) {
            Text(text = "Spesa", style = MaterialTheme.typography.bodyMedium)
            Text(text = "04/04/2024", style = MaterialTheme.typography.bodySmall)
        }
        Text(
            text = "-$24.50",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TransactionPreview() {
    MxfrontendTheme {
        Transaction(modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
        )
    }
}