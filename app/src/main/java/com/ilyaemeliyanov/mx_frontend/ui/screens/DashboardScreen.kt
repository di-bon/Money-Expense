package com.ilyaemeliyanov.mx_frontend.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilyaemeliyanov.mx_frontend.data.transactions.Transaction
import com.ilyaemeliyanov.mx_frontend.data.user.Currency
import com.ilyaemeliyanov.mx_frontend.ui.UiState
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXAlertDialog
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXCard
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXChartScreen
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXDropdownMenu
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXRecentTransactions
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXTitle
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXColors
import com.ilyaemeliyanov.mx_frontend.ui.theme.euclidCircularA
import com.ilyaemeliyanov.mx_frontend.utils.StringFormatter
import com.ilyaemeliyanov.mx_frontend.viewmodel.MXViewModel

@Composable
fun DashboardScreen(
    mxViewModel: MXViewModel,
    uiState: UiState,
    isLoading: Boolean,
) {
    var last10Transactions = mxViewModel.getLast10Transactions(mxViewModel.transactions)

    LaunchedEffect(mxViewModel.transactions) {
        last10Transactions = mxViewModel.getLast10Transactions(mxViewModel.transactions)
    }

    Column(modifier = Modifier.padding(32.dp)) {
        DashboardTopBar(
            onWalletSelection = mxViewModel::setSelectedWallet,
            mxViewModel = mxViewModel
        )
        DashboardInfo(
            getIncome = mxViewModel::getIncome,
            getExpenses = mxViewModel::getExpenses,
            mxViewModel = mxViewModel,
            modifier = Modifier
                .padding(vertical = 12.dp)
        )

        MXCard(
            containerColor = Color.White,
            contentColor = Color.Black
        ) {
            MXRecentTransactions(
                mxViewModel = mxViewModel,
                transactionList = last10Transactions,
                isLoading = isLoading,
                modifier = Modifier
                    .fillMaxHeight()
            )
        }
    }
}

@Composable
private fun DashboardTopBar(
    onWalletSelection: (String) -> Unit,
    mxViewModel: MXViewModel,
    modifier: Modifier = Modifier
) {
    val wallets = mxViewModel.wallets

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
                items = listOf("All wallets") + wallets.map { wallet -> wallet.name },
                selectedItem = if (mxViewModel.selectedWallet != null) mxViewModel.selectedWallet?.name else "All wallets",
                showLabel = false
            ) { item ->
                onWalletSelection(item)
            }
        }
    }
}

@Composable
private fun DashboardInfo(
    getIncome: (List<Transaction>) -> Float,
    getExpenses: (List<Transaction>) -> Float,
    mxViewModel: MXViewModel,
    modifier: Modifier = Modifier
) {
    val transactions = if (mxViewModel.selectedWallet != null) mxViewModel.transactions.filter { it.wallet.id == mxViewModel.selectedWallet?.id } else mxViewModel.transactions
    var income by remember { mutableFloatStateOf(mxViewModel.income) }
    var expenses by remember { mutableFloatStateOf(mxViewModel.expenses) }
    var balance by remember { mutableFloatStateOf(mxViewModel.balance) }

    var showChartContextDialog by remember { mutableStateOf(false) }
    var incomeChartContextDialog by remember { mutableStateOf(false) }
    var expensesChartContextDialog by remember { mutableStateOf(false) }

    LaunchedEffect(transactions) {// on transaction change
        income = getIncome(transactions)
        expenses = getExpenses(transactions)
        balance = income + expenses
    }

    Column(modifier = modifier) {
        MXCard(
            containerColor = MXColors.Default.ActiveColor,
            contentColor = MXColors.Default.PrimaryColor,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
                .clickable { showChartContextDialog = true }
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Balance",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text =
                        if (balance >= 0f)
                            "+ ${mxViewModel.user?.currency?.symbol ?: Currency.US_DOLLAR.symbol} ${StringFormatter.getFormattedAmount(balance)}"
                        else
                            "- ${mxViewModel.user?.currency?.symbol ?: Currency.US_DOLLAR.symbol} ${StringFormatter.getFormattedAmount(balance)}",
                        fontFamily = euclidCircularA,
                        fontWeight = FontWeight.Normal,
                        fontSize = 42.sp
                    )
                }
                Icon(
                    imageVector = Icons.Filled.BarChart,
                    tint = Color.Black,
                    contentDescription = "Chart",
                    modifier = Modifier.size(40.dp)
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            MXCard(
                containerColor = MXColors.Default.PrimaryColor,
                contentColor = MXColors.Default.SecondaryColor,
                modifier = Modifier
                    .weight(1f)
                    .clickable { incomeChartContextDialog = true }
            ) {
                Text(
                    text = "Income",
                    style = MaterialTheme.typography.labelLarge,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "+ ${mxViewModel.user?.currency?.symbol ?: Currency.US_DOLLAR.symbol} ${StringFormatter.getFormattedAmount(income)}",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            MXCard(
                containerColor = Color.Black,
                contentColor = MXColors.Default.SecondaryColor,
                modifier = Modifier
                    .weight(1f)
                    .clickable { expensesChartContextDialog = true }
            ) {
                Text(
                    text = "Expenses",
                    style = MaterialTheme.typography.labelLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "- ${mxViewModel.user?.currency?.symbol ?: Currency.US_DOLLAR.symbol} ${StringFormatter.getFormattedAmount(expenses)}",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White,
                )
            }
        }

        if (showChartContextDialog) {
            MXAlertDialog(
                title = "Transaction Chart",
                dismissLabel = "Dismiss",
                confirmLabel = "Done",
                onDismiss = { showChartContextDialog = false },
                onConfirm = { showChartContextDialog = false }
            ) {
                MXChartScreen(
                    mxViewModel = mxViewModel,
                    transactionList = transactions
                )
            }
        }
        if (incomeChartContextDialog) {
            MXAlertDialog(
                title = "Income Chart",
                dismissLabel = "Dismiss",
                confirmLabel = "Done",
                onDismiss = { incomeChartContextDialog = false },
                onConfirm = { incomeChartContextDialog = false }
            ) {
                MXChartScreen(
                    mxViewModel = mxViewModel,
                    transactionList = transactions.filter { it.amount > 0 }
                )
            }
        }
        if (expensesChartContextDialog) {
            MXAlertDialog(
                title = "Expenses Chart",
                dismissLabel = "Dismiss",
                confirmLabel = "Done",
                onDismiss = { expensesChartContextDialog = false },
                onConfirm = { expensesChartContextDialog = false }
            ) {
                MXChartScreen(
                    mxViewModel = mxViewModel,
                    transactionList = transactions.filter { it.amount < 0 }
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun DashboardInfoPreview() {
//    MXTheme {
//        val mxViewModel: MXViewModel = MXViewModelSingleton.getInstance()
//        DashboardInfo(
//            getIncome = mxViewModel::getIncome,
//            getExpenses = mxViewModel::getExpenses,
//            mxViewModel = mxViewModel,
//            modifier = Modifier
//                .padding(16.dp)
//        )
//    }
//}

