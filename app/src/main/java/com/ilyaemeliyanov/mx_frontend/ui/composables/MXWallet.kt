package com.ilyaemeliyanov.mx_frontend.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.ilyaemeliyanov.mx_frontend.data.user.Currency
import com.ilyaemeliyanov.mx_frontend.data.wallets.Wallet
import com.ilyaemeliyanov.mx_frontend.ui.theme.euclidCircularA
import com.ilyaemeliyanov.mx_frontend.ui.theme.spaceGrotesk
import com.ilyaemeliyanov.mx_frontend.utils.StringFormatter
import com.ilyaemeliyanov.mx_frontend.viewmodel.MXViewModel
import kotlinx.coroutines.launch

@Composable
fun MXWallet(
    wallet: Wallet,
    cardColor: Color,
    mxViewModel: MXViewModel,
    modifier: Modifier = Modifier
) {
    val transactions = mxViewModel.transactions.filter { it.wallet.id == wallet.id }
    var income by remember { mutableFloatStateOf(0.0f) }
    var expenses by remember { mutableFloatStateOf(0.0f) }

    var showEditContextDialog by remember { mutableStateOf(false) }
    var showDeleteContextDialog by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf(wallet.name) }
    var description by remember { mutableStateOf(wallet.description ?: "") }
    var amount by remember { mutableStateOf(wallet.amount.toString()) }

    var isEditNameValid by remember { mutableStateOf(true) }
    var isDescriptionEditValid by remember { mutableStateOf(true) }
    var isAmountEditValid by remember { mutableStateOf(true) }

    LaunchedEffect(transactions.isNotEmpty()) {
        val amounts = transactions.map { it.amount }
        if (amounts.isNotEmpty()) {
            income = mxViewModel.getIncome(transactions)
            expenses = mxViewModel.getExpenses(transactions)
        }
    }

    MXCard(
        containerColor = cardColor,
        contentColor = Color.Black,
        modifier = modifier
    ) {
        Column (
            verticalArrangement = Arrangement.Top,
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(IntrinsicSize.Min)
            ) {
                Text(
                    text = wallet.name,
                    style = TextStyle(
                        fontFamily = spaceGrotesk,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit wallet",
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(10.dp)
                        .clip(RoundedCornerShape(30.dp))
                        .clickable { showEditContextDialog = true }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete wallet",
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(10.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { showDeleteContextDialog = true }
                )
            }
            Text(
                text = "${mxViewModel.user?.currency?.symbol ?: Currency.US_DOLLAR.symbol} ${StringFormatter.getFormattedAmount(wallet.amount + income + expenses)}",
                style = TextStyle(fontFamily = euclidCircularA, fontSize = 24.sp, fontWeight = FontWeight.Medium),
                modifier = Modifier
                    .height(IntrinsicSize.Min)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = wallet.description.toString(),
                style = MaterialTheme.typography.bodySmall,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )

            if (showEditContextDialog) {
                MXAlertDialog(
                    "Edit Wallet",
                    confirmLabel = "Update",
                    dismissLabel = "Cancel",
                    onDismiss = { showEditContextDialog = false },
                    onConfirm = {
                        name = name.trim()
                        isEditNameValid = mxViewModel.validateContent(name)

                        description = description.trim()
                        isDescriptionEditValid = mxViewModel.validateContent(description)

                        isAmountEditValid = mxViewModel.validateAmount(amount)

                        if (isEditNameValid && isDescriptionEditValid && isAmountEditValid) {
                            val newWallet = Wallet(
                                id = wallet.id,
                                name = name,
                                amount = String.format("%.2f", amount.toFloat()).toFloat(),
                                description = description,
                                ref = null,
                            )
                            mxViewModel.updateWallet(newWallet)
                            showEditContextDialog = false
                        }
                    }
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                    MXInput(
                        titleText = "Name",
                        labelText = "Edit wallet name...",
                        text = name,
                        onTextChange = { name = it },
                        modifier = Modifier.padding(vertical = 8.dp),
                        isError = !isEditNameValid,
                        errorMessage = "Enter a valid name"
                    )
                    MXInput(
                        titleText = "Description",
                        labelText = "Edit wallet description...",
                        text = description,
                        onTextChange = { value -> description = value },
                        modifier = Modifier.padding(vertical = 8.dp),
                        isError = !isDescriptionEditValid,
                        errorMessage = "Enter a valid description"
                    )
                    MXInput(
                        titleText = "Amount",
                        labelText = "Edit initial amount...",
                        text = amount,
                        onTextChange = { value -> amount = value },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier.padding(vertical = 8.dp),
                        isError = !isAmountEditValid,
                        errorMessage = "Enter a valid amount (use . for decimal values)"
                    )
                }
            }

            if (showDeleteContextDialog) {
                MXAlertDialog(
                    "Delete Wallet",
                    confirmLabel = "Delete",
                    dismissLabel = "Cancel",
                    onDismiss = { showDeleteContextDialog = false },
                    onConfirm = {
                        mxViewModel.viewModelScope.launch {
                            showDeleteContextDialog = false
                            mxViewModel.deleteWallet(wallet)
                        }
                    }
                ) {
                    Text(
                        "Are you sure you want to delete this wallet?\n\nAll the associated transactions will be delete as well",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun MXWalletPreview() {
//    MXTheme {
//        MXWallet(
//            wallet = Wallet(
//                name = "PayPal",
//                amount = 1234.00f,
//                description = "Wallet for tracing transactions from and to my personal paypal account"
//            ),
//            cardColor = Color(red = 105, green = 247, blue = 179),
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(IntrinsicSize.Min)
//                .padding(32.dp)
//        )
//    }
//}