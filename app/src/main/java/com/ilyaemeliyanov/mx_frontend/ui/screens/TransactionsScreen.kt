package com.ilyaemeliyanov.mx_frontend.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ilyaemeliyanov.mx_frontend.data.user.Currency
import com.ilyaemeliyanov.mx_frontend.ui.UiState
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXAlertDialog
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXCard
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXDatePicker
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXDropdownMenu
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXInput
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXRecentTransactions
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXRectangularButton
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXTitle
import com.ilyaemeliyanov.mx_frontend.ui.composables.MxCircluarButton
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXColors
import com.ilyaemeliyanov.mx_frontend.utils.StringFormatter
import com.ilyaemeliyanov.mx_frontend.utils.StringFormatter.getDateFromString
import com.ilyaemeliyanov.mx_frontend.utils.StringFormatter.getStringFromDate
import com.ilyaemeliyanov.mx_frontend.utils.TransactionType
import com.ilyaemeliyanov.mx_frontend.viewmodel.MXViewModel
import java.util.Date

@Composable
fun TransactionsScreen(
    mxViewModel: MXViewModel,
    uiState: UiState,
    isLoading: Boolean,
) {
    val context = LocalContext.current

    var showContextDialog by remember { mutableStateOf(false) }
    var filteredAndSortedTransactions = mxViewModel.getFilteredAndSortedTransactions(
        transactionList = mxViewModel.transactions,
        filter = uiState.currentFilter,
        sort = uiState.currentSortingCriteria
    )
    var sum = filteredAndSortedTransactions.fold(0.0f) { acc, transaction -> acc + transaction.amount }

    var isTransactionLabelValid by remember { mutableStateOf(true) }
    var isTransactionAmountValid by remember { mutableStateOf(true) }
    var isTransactionWalletValid by remember { mutableStateOf(true) }

    LaunchedEffect(mxViewModel.transactions) {
        filteredAndSortedTransactions = mxViewModel.getFilteredAndSortedTransactions(
            transactionList = mxViewModel.transactions,
            filter = uiState.currentFilter,
            sort = uiState.currentSortingCriteria
        )
        sum = filteredAndSortedTransactions.fold(0.0f) { acc, transaction -> acc + transaction.amount }
    }

    Column (
        modifier = Modifier
            .padding(32.dp)
    ) {
        MXTitle(
            title = "Transactions",
            modifier = Modifier.fillMaxWidth()
        ) {
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
                    mxViewModel.transactionLabel = mxViewModel.transactionLabel.trim()
                    isTransactionLabelValid = mxViewModel.validateContent(mxViewModel.transactionLabel)

                    isTransactionAmountValid = mxViewModel.validateAmount(mxViewModel.transactionAmount)

                    isTransactionWalletValid = mxViewModel.transactionWalletName in mxViewModel.wallets.map { it.name }

                    if (isTransactionLabelValid && isTransactionAmountValid && isTransactionWalletValid) {
                        mxViewModel.createAndSaveTransaction()
                        showContextDialog = false
                        mxViewModel.transactionLabel = ""
                        mxViewModel.transactionDescription = ""
                        mxViewModel.transactionAmount = ""
                        mxViewModel.walletName = "Select wallet..."
                    } else {
                        Toast.makeText(context, "Make sure to enter valid input!", Toast.LENGTH_LONG).show()
                    }
                }
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MXRectangularButton(
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp),
                        containerColor =
                        if (mxViewModel.transactionType == TransactionType.EXPENSE)
                            MXColors.Default.ActiveColor
                        else
                            MXColors.Default.SecondaryColor,
                        contentColor =
                        if (mxViewModel.transactionType == TransactionType.EXPENSE)
                            MXColors.Default.PrimaryColor
                        else
                            Color.White,
                        onClick = { mxViewModel.transactionType = TransactionType.EXPENSE }
                    ) {
                        Text("OUT")
                    }
                    MXRectangularButton(
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp),
                        containerColor =
                        if (mxViewModel.transactionType == TransactionType.INCOME)
                            MXColors.Default.ActiveColor
                        else
                            MXColors.Default.SecondaryColor,
                        contentColor =
                        if (mxViewModel.transactionType == TransactionType.INCOME)
                            MXColors.Default.PrimaryColor
                        else
                            Color.White,
                        onClick = { mxViewModel.transactionType = TransactionType.INCOME }
                    ) {
                        Text("IN")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                MXInput(
                    titleText = "Label",
                    labelText = "Enter transaction label...",
                    text = mxViewModel.transactionLabel,
                    onTextChange = { mxViewModel.transactionLabel = it },
                    modifier = Modifier.padding(vertical = 8.dp),
                    isError = !isTransactionLabelValid,
                    errorMessage = "Enter a valid label"
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
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.padding(vertical = 8.dp),
                    isError = !isTransactionAmountValid,
                    errorMessage = "Enter a valid amount (use . for decimal values)"
                )

                Column {
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
                            selectedItem =
                            if (mxViewModel.transactionWalletName != "")
                                mxViewModel.transactionWalletName
                            else
                                "Select wallet...",
                            showLabel = false
                        ) {
                            mxViewModel.transactionWalletName = it
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                Column {
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
                Spacer(modifier = Modifier.weight(1f))
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
            Spacer(modifier = Modifier.width(8.dp))

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
                text =
                if (sum >= 0f)
                    "Sum: + ${mxViewModel.user?.currency?.symbol ?: Currency.US_DOLLAR.symbol} ${StringFormatter.getFormattedAmount(sum)}"
                else
                    "Sum: - ${mxViewModel.user?.currency?.symbol ?: Currency.US_DOLLAR.symbol} ${StringFormatter.getFormattedAmount(sum)}",
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
                mxViewModel = mxViewModel,
                showTitle = false,
                transactionList = filteredAndSortedTransactions,
                isLoading = isLoading
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun TransactionsScreenPreview() {
//    MXTheme {
//        val vm: MXViewModel = viewModel()
//        TransactionsScreen(
//            mxViewModel = vm,
//            uiState = vm.uiState.collectAsState().value,
//            isLoading = false,
//        )
//    }
//}