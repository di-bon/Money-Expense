package com.ilyaemeliyanov.mx_frontend.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
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
import com.ilyaemeliyanov.mx_frontend.data.transactions.Transaction
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

@Composable
fun DashboardScreen(
    getLast10Transactions: (List<Transaction>) -> List<Transaction>,
    onWalletSelection: (String) -> Unit,
    getIncome: (List<Transaction>) -> Float,
    getExpenses: (List<Transaction>) -> Float,
    getCurrentWalletTransactions: (List<Transaction>) -> List<Transaction>,
    modifier: Modifier = Modifier
) {

    // ---
    // TODO: to be replaced by UiState class
    val mxViewModel: MXViewModel = MXViewModelSingleton.getInstance()
    val user = mxViewModel.user
    val wallets = mxViewModel.wallets
    val transactions = mxViewModel.transactions
    // ---

    Column(modifier = modifier) {
        DashboardTopBar(
            onWalletSelection = onWalletSelection
        )
        DashboardInfo(
            walletTransactions = getCurrentWalletTransactions(mxViewModel.transactions),
            getIncome = getIncome,
            getExpenses = getExpenses,
            modifier = Modifier
                .padding(vertical = 12.dp)
        )
        Text("Welcome back ${user?.email}")

        MXCard(
            containerColor = Color.White,
            contentColor = Color.Black
        ) {
            RecentTransactions(
                transactionList = getLast10Transactions(mxViewModel.transactions)
            )
        }
    }
}

@Composable
private fun DashboardTopBar(
    onWalletSelection: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val mxViewModel: MXViewModel = MXViewModelSingleton.getInstance()
    val wallets = listOf(null) + mxViewModel.wallets

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        MXTitle(
            title = "Dashboard",
            modifier = Modifier.fillMaxWidth()
        ) {
            MXDropdownMenu(
                label = "Wallet",
                items = wallets.map { wallet -> wallet?.name ?: "All wallets" },
                selectedItem = "All wallets",
                showLabel = false
            ) { item ->
                onWalletSelection(item)
            }
        }
    }
}

// TODO: delete 'CurrentWaller' function
//@Composable
//private fun CurrentWallet(
//    onClick: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Surface(
//        onClick = onClick,
//        modifier = modifier,
//        color = Color.Transparent
//    ) {
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        modifier = Modifier.padding(16.dp)
//    ) {
//            Text(
//                text = "Wallet name",
//                style = MaterialTheme.typography.labelMedium
//            )
//            Spacer(modifier = Modifier.width(4.dp))
//            Icon(
//                imageVector = Icons.Filled.KeyboardArrowDown,
//                contentDescription = "Change current wallet",
//                modifier = Modifier
//                    .size(16.dp)
//            )
//        }
//    }
//}

@Composable
private fun DashboardInfo(
    walletTransactions: List<Transaction>,
    getIncome: (List<Transaction>) -> Float,
    getExpenses: (List<Transaction>) -> Float,
    modifier: Modifier = Modifier
) {

    // Unused variables
//    val mxViewModel: MXViewModel = MXViewModelSingleton.getInstance()
//    val balance = mxViewModel.balance
//    val income = mxViewModel.income
//    val expenses = mxViewModel.expenses

    val income = getIncome(walletTransactions)
    val expenses = getExpenses(walletTransactions)
    val balance = income - expenses

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
                text = if (balance >= 0f) "+ \$ ${StringFormatter.getFormattedAmount(balance)}" else "- \$ ${StringFormatter.getFormattedAmount(balance)}",
//                text = "\$ ${StringFormatter.getFormattedAmount(balance)}",
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
                    text = "+ \$ ${StringFormatter.getFormattedAmount(income)}",
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
                    text = "- \$ ${StringFormatter.getFormattedAmount(expenses)}",
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
            walletTransactions = listOf(),
            getIncome = { 0.0f },
            getExpenses = { 0.0f },
            modifier = Modifier
                .padding(16.dp)
        )
    }
}

