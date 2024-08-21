package com.ilyaemeliyanov.mx_frontend.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import com.ilyaemeliyanov.mx_frontend.data.transactions.Transaction
import com.ilyaemeliyanov.mx_frontend.ui.UiState
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXCard
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXDropdownMenu
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXRecentTransactions
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXTitle
import com.ilyaemeliyanov.mx_frontend.ui.composables.ShimmerListItem
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXColors
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXTheme
import com.ilyaemeliyanov.mx_frontend.ui.theme.euclidCircularA
import com.ilyaemeliyanov.mx_frontend.utils.StringFormatter
import com.ilyaemeliyanov.mx_frontend.viewmodel.MXViewModel
import com.ilyaemeliyanov.mx_frontend.viewmodel.MXViewModelSingleton

@Composable
fun DashboardScreen(
    mxViewModel: MXViewModel,
    uiState: UiState,
    isLoading: Boolean,
) {
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
                transactionList = mxViewModel.getLast10Transactions(mxViewModel.transactions),
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
    val wallets = mxViewModel.wallets ?: emptyList()

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

@Composable
private fun DashboardInfo(
    getIncome: (List<Transaction>) -> Float,
    getExpenses: (List<Transaction>) -> Float,
    mxViewModel: MXViewModel,
    modifier: Modifier = Modifier
) {
    val income = getIncome(mxViewModel.transactions)
    val expenses = getExpenses(mxViewModel.transactions)
    val balance = expenses + income

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
        val mxViewModel: MXViewModel = MXViewModelSingleton.getInstance()
        DashboardInfo(
            getIncome = mxViewModel::getIncome,
            getExpenses = mxViewModel::getExpenses,
            mxViewModel = mxViewModel,
            modifier = Modifier
                .padding(16.dp)
        )
    }
}

