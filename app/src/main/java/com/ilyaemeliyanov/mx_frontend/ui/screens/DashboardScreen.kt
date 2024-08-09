package com.ilyaemeliyanov.mx_frontend.ui.screens

import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ilyaemeliyanov.mx_frontend.data.user.User
import com.ilyaemeliyanov.mx_frontend.data.user.UserRepository
import com.ilyaemeliyanov.mx_frontend.data.wallets.Wallet
import com.ilyaemeliyanov.mx_frontend.viewmodel.MXViewModelFactory
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXCard
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXTitle
import com.ilyaemeliyanov.mx_frontend.ui.composables.RecentTransactions
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXColors
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXTheme
import com.ilyaemeliyanov.mx_frontend.ui.theme.euclidCircularA
import com.ilyaemeliyanov.mx_frontend.viewmodel.MXViewModel

private const val TAG = "DashboardScreen"

@Composable
fun DashboardScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val repository = UserRepository()
    val mxViewModel: MXViewModel = viewModel(factory = MXViewModelFactory(repository))

    var user by remember { mxViewModel.user }
    var wallets = remember { mxViewModel.wallets }
    var transactions = remember { mxViewModel.transactions }

    LaunchedEffect(Unit) {
        val email = "john.doe@gmail.com"
        mxViewModel.loadData(email)
    }


    Column(modifier = modifier) {
        DashboardTopBar()
        // Spacer(modifier = Modifier.height(20.dp))
        DashboardInfo(
            modifier = Modifier
                .padding(vertical = 12.dp)
        )
        // Spacer(modifier = Modifier.height(20.dp))
        Text("Welcome back ${mxViewModel.user.value?.email}")

        MXCard(
            containerColor = Color.White,
            contentColor = Color.Black
        ) {
            RecentTransactions(
                transactionList = mxViewModel.transactions // TODO: Add transactions from firebase
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun DashboardScreenPreview() {
//    MXTheme {
//        DashboardScreen(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(color = Color(246, 246, 246))
//                .padding(24.dp)
//        )
//    }
//}

@Composable
private fun DashboardTopBar(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        MXTitle(
            title = "Dashboard",
            modifier = Modifier.fillMaxWidth()
        ) {
            CurrentWallet(onClick = {})
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
                text = "\$ 1234.00",
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
                    text = "Incomes",
                    style = MaterialTheme.typography.labelLarge,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "+ \$ 234.35",
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
                    text = "- \$ 123.45",
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

