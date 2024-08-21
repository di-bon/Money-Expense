package com.ilyaemeliyanov.mx_frontend.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ilyaemeliyanov.mx_frontend.ui.UiState
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXAlertDialog
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXCard
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXDatePicker
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXDropdownMenu
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXInput
import com.ilyaemeliyanov.mx_frontend.ui.composables.MxCircluarButton
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXTitle
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXRecentTransactions
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXColors
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXTheme
import com.ilyaemeliyanov.mx_frontend.utils.StringFormatter
import com.ilyaemeliyanov.mx_frontend.utils.StringFormatter.getDateFromString
import com.ilyaemeliyanov.mx_frontend.utils.StringFormatter.getStringFromDate
import com.ilyaemeliyanov.mx_frontend.viewmodel.MXViewModel
import java.util.Date

private const val TAG = "TransactionsScreen"

@Composable
fun TransactionsScreen(
    uiState: UiState,
    mxViewModel: MXViewModel,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    var showContextDialog by remember { mutableStateOf(false) }

    val filteredAndSortedTransactions = mxViewModel.getFilteredAndSortedTransactions(
        transactionList = mxViewModel.transactions,
        filter = uiState.currentFilter,
        sort = uiState.currentSortingCriteria
    )

    val sum = filteredAndSortedTransactions.fold(0.0f) { acc, transaction -> acc + transaction.amount }

    Column (
        modifier = modifier
//            .fillMaxHeight()
    ) {
        MXTitle(title = "Transactions", modifier = Modifier.fillMaxWidth()) {
            MxCircluarButton(
                onClick = { if(!showContextDialog) showContextDialog = true },
                modifier = Modifier
                    .padding(8.dp)
                    .size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        if (showContextDialog) {
            MXAlertDialog(
                title = "Create Transaction",
                dismissLabel = "Cancel",
                confirmLabel = "Create",
                onDismiss = { showContextDialog = false },
                onConfirm = {
                    mxViewModel.createAndSaveTransaction()
                    showContextDialog = false
                }
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                MXInput(
                    titleText = "Label",
                    labelText = "Enter transaction label...",
                    text = mxViewModel.transactionLabel,
                    onTextChange = { mxViewModel.transactionLabel = it },
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                MXInput(
                    titleText = "Description",
                    labelText = "Enter a short transaction description...",
                    text = mxViewModel.transactionDescription,
                    onTextChange = { mxViewModel.transactionDescription = it },
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                MXInput(
                    titleText = "Amount",
                    labelText = "Enter transaction value...",
                    text = mxViewModel.transactionAmount,
                    onTextChange = { mxViewModel.transactionAmount = it },
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Column() {
                    Text("Wallet")
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .border(
                            1.dp,
                            brush = SolidColor(Color.Gray),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        MXDropdownMenu(
                            items = mxViewModel.wallets.map { it.name },
                            selectedItem = mxViewModel.transactionWalletName,
                            showLabel = false
                        ) {
                            mxViewModel.transactionWalletName = it
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                Column() {
                    Text("Date")
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(modifier = Modifier
                        .fillMaxWidth()
                    ) {
                        MXDatePicker(
                            getStringFromDate(mxViewModel.transactionDate)
                        ) {
                            mxViewModel.transactionDate = getDateFromString(it) ?: Date()
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            MXCard(
                containerColor = MXColors.Default.ActiveColor,
                contentColor = Color.Black,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                MXDropdownMenu(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = "Filter by",
                    items = listOf(
                        "None",
                        "Positive",
                        "Negative"
                    ),
                    selectedItem = uiState.currentFilter
                ) {
                    mxViewModel.updateCurrentFilter(it)
                }
            }
            Spacer(modifier = Modifier.width(24.dp))

            MXCard (
                containerColor = MXColors.Default.ActiveColor,
                contentColor = Color.Black,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                MXDropdownMenu(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = "Sort by",
                    items = listOf(
                        "Newest",
                        "Oldest",
                        "Biggest",
                        "Smallest"
                    ),
                    selectedItem = uiState.currentSortingCriteria
                ) {
                    mxViewModel.updateCurrentSortingCriteria(it)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(8.dp))
                .background(color = Color.Black)
        ) {
            Text(
                text = if (sum >= 0f)
                    "Sum: + \$ ${StringFormatter.getFormattedAmount(sum)}"
                    else "Sum: - \$ ${StringFormatter.getFormattedAmount(sum)}",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        MXCard(
            containerColor = Color.White
        ) {
            MXRecentTransactions(
                showTitle = false,
                transactionList = filteredAndSortedTransactions,
                isLoading = isLoading
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TransactionsScreenPreview() {
    MXTheme {
        val vm: MXViewModel = viewModel()
        TransactionsScreen(
            mxViewModel = vm,
            uiState = vm.uiState.collectAsState().value,
            isLoading = false,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = MXColors.Default.BgColor)
                .padding(16.dp)
        )
    }
}