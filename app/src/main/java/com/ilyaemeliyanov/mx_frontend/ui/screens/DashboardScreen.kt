package com.ilyaemeliyanov.mx_frontend.ui.screens

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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXCard
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXDropdownMenu
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXTitle
import com.ilyaemeliyanov.mx_frontend.ui.composables.RecentTransactions
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXColors
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXTheme
import com.ilyaemeliyanov.mx_frontend.ui.theme.euclidCircularA
import com.ilyaemeliyanov.mx_frontend.utils.StringFormatter
import com.ilyaemeliyanov.mx_frontend.viewmodel.MXViewModel
import com.ilyaemeliyanov.mx_frontend.viewmodel.MXViewModelSingleton
import kotlin.math.max
import kotlin.math.min

@Composable
fun DashboardScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val mxViewModel: MXViewModel = MXViewModelSingleton.getInstance()

    val user = mxViewModel.user
    val wallets = mxViewModel.wallets
    val transactions = mxViewModel.transactions

    Column(modifier = modifier) {
        DashboardTopBar()
        // Spacer(modifier = Modifier.height(20.dp))
        DashboardInfo(
            modifier = Modifier
                .padding(vertical = 12.dp)
        )
        // Spacer(modifier = Modifier.height(20.dp))
        Text("Welcome back ${user?.email}")

        MXCard(
            containerColor = Color.White,
            contentColor = Color.Black
        ) {
            RecentTransactions(
                transactionList = transactions.subList(0, max(0, min(transactions.size, 10)))
            )
        }
    }
}

@Composable
private fun DashboardTopBar(
    modifier: Modifier = Modifier
) {
    val mxViewModel: MXViewModel = MXViewModelSingleton.getInstance()
    val wallets = mxViewModel.wallets

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        MXTitle(
            title = "Dashboard",
            modifier = Modifier.fillMaxWidth()
        ) {
            MXDropdownMenu(label = "Wallet", items = wallets.map { wallet -> wallet.name }, selectedItem = null) {item ->
                for (wallet in wallets) {
                    if (wallet.name == item) {
                        mxViewModel.selectedWallet = wallet
                    }
                }
//                mxViewModel.selectedWallet = item
            }
        }
    }
}

// TODO: replace with MXDropdownMenu
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
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)
    ) {
            Text(
                text = "Wallet name",
                style = MaterialTheme.typography.labelMedium
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
    val mxViewModel: MXViewModel = MXViewModelSingleton.getInstance()
    val balance = mxViewModel.balance
    val income = mxViewModel.income
    val expenses = mxViewModel.expenses

    Column(modifier = modifier) {
        MXCard(
            containerColor = MXColors.Default.ActiveColor,
            contentColor = MXColors.Default.PrimaryColor,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        ) {
            Text(
                text = "Balance",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "\$ ${StringFormatter.getFormattedAmount(balance)}", // TODO: format balance to add .2f at the end
                fontFamily = euclidCircularA,
                fontWeight = FontWeight.Normal,
                fontSize = 42.sp
            )
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            MXCard(
                containerColor = MXColors.Default.PrimaryColor,
                contentColor = MXColors.Default.SecondaryColor,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Income",
                    style = MaterialTheme.typography.labelLarge,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "+ \$ ${StringFormatter.getFormattedAmount(income)}", // TODO: format to add .2f at the end
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            MXCard(
                containerColor = Color.Black,
                contentColor = MXColors.Default.SecondaryColor,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Expenses",
                    style = MaterialTheme.typography.labelLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "- \$ ${StringFormatter.getFormattedAmount(expenses)}", // TODO: format to add .2f at the end
                    style = MaterialTheme.typography.titleSmall,
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

