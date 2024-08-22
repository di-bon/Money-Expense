package com.ilyaemeliyanov.mx_frontend.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Downloading
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PersonOff
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ilyaemeliyanov.mx_frontend.data.user.Currency
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXAlertDialog
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXDropdownMenu
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXInput
import com.ilyaemeliyanov.mx_frontend.viewmodel.MXRepository
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXSettingsButton
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXTitle
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXColors
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXShapes
import com.ilyaemeliyanov.mx_frontend.viewmodel.MXViewModel
import com.ilyaemeliyanov.mx_frontend.viewmodel.MXViewModelSingleton
import com.ilyaemeliyanov.mx_frontend.utils.CSVConverter
import com.ilyaemeliyanov.mx_frontend.utils.CSVConverter.exportToCSV
import com.ilyaemeliyanov.mx_frontend.utils.CSVConverter.toCSV
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@Composable
fun SettingsScreen(
    mxViewModel: MXViewModel,
    modifier: Modifier = Modifier
) {

//    val mxViewModel: MXViewModel = remember { MXViewModelSingleton.getInstance() }

    val context = LocalContext.current
    val user = mxViewModel.user

    var showExportContextDialog by remember { mutableStateOf(false) }
    var showCurrencyContextDialog by remember { mutableStateOf(false) }
    var selectedCurrency by remember { mutableStateOf(mxViewModel.user?.currency?.symbol ?: Currency.US_DOLLAR.symbol) }

    Box {
        LazyColumn (modifier = Modifier.padding(32.dp)) {
            item {
                MXTitle(
                    title = "Settings",
                    modifier = Modifier
                        .fillMaxWidth()
                ) { }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = MXShapes.medium)
                        .background(color = Color.Black)
                        .padding(vertical = 16.dp, horizontal = 12.dp)
                ) {
                    Text(
                        text = "Welcome back,",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                    Text(
                        text = "${user?.firstName} ${user?.lastName}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MXColors.Default.ActiveColor
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(text = "General", style = MaterialTheme.typography.labelLarge)
                    Spacer(modifier = Modifier.height(12.dp))
                    MXSettingsButton(
                        onClick = { /*TODO*/ },
                        leftIconImageVector = Icons.Outlined.Person,
                        titleString = "Change personal info",
                        descriptionString = "Here you can change your firstname, lastname and password",
                        rightIconImageVector = Icons.Filled.KeyboardArrowRight
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    MXSettingsButton(
                        onClick = { showExportContextDialog = true },
                        leftIconImageVector = Icons.Filled.Downloading, // TODO: set download icon
                        titleString = "Export transactions in CSV",
                        descriptionString = "Your transactions are available for download in CSV format",
                        rightIconImageVector = Icons.Filled.KeyboardArrowRight
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    MXSettingsButton(
                        onClick = { showCurrencyContextDialog = true },
                        leftIconImageVector = Icons.Outlined.AttachMoney, // TODO: set credit card icon
                        titleString = "Change currency",
                        descriptionString = "Choose the currency to display your transactions with",
                        rightIconImageVector = Icons.Filled.KeyboardArrowRight
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(text = "Danger zone", style = MaterialTheme.typography.labelLarge)
                    Spacer(modifier = Modifier.height(12.dp))
                    MXSettingsButton(
                        onClick = { /*TODO*/ },
                        leftIconImageVector = Icons.Outlined.PersonOff, // TODO: set delete account icon
                        titleString = "Close & delete account",
                        titleColor = Color.Red,
                        descriptionString = "Close and delete your account and all related wallets and tra...",
                        rightIconImageVector = Icons.Filled.KeyboardArrowRight
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        if (showExportContextDialog) {
            MXAlertDialog(
                title = "Export transactions",
                dismissLabel = "Cancel",
                confirmLabel = "Export",
                onDismiss = { showExportContextDialog = false },
                onConfirm = {
                    val csvData = mxViewModel.transactions.toCSV()
                    val file = exportToCSV(context, "transactions", csvData)
                    file?.let {
                        // Handle successful export, show Toast with the path for the exported file
                        Toast.makeText(context, "Exported to: ${it.absolutePath}", Toast.LENGTH_LONG).show()
                    } ?: run {
                        // Handle failure
                        Toast.makeText(context, "Failed to export", Toast.LENGTH_LONG).show()
                    }
                }) {
                Text("Your files will be exported to your local storage \u60b0")
            }
        }

        if (showCurrencyContextDialog) {
            MXAlertDialog(
                title = "Change Currency",
                dismissLabel = "Cancel",
                confirmLabel = "Save",
                onDismiss = { showCurrencyContextDialog = false },
                onConfirm = {
                    val newCurrency = Currency.entries.firstOrNull { it.symbol == selectedCurrency } ?: Currency.US_DOLLAR
                    mxViewModel.user?.currency = newCurrency
                    val newUser = mxViewModel.user
                    newUser?.currency = newCurrency
                    mxViewModel.updateUser(newUser)
                    showCurrencyContextDialog = false
                }) {
                Column {
                    Text("Select a currency...")
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
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
                            items = Currency.entries.map { it.symbol },
                            selectedItem = selectedCurrency,
                            showLabel = false
                        ) {
                            selectedCurrency = it
                        }
                    }
                }
            }
        }
    }

}

//@Preview(showBackground = true)
//@Composable
//private fun SettingsScreenPreview() {
//    MXTheme {
//        SettingsScreen(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp)
//        )
//    }
//}