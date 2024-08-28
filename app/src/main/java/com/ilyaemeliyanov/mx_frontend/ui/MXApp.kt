package com.ilyaemeliyanov.mx_frontend.ui

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.material.BottomNavigation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.CompareArrows
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.ilyaemeliyanov.mx_frontend.AuthScreens
import com.ilyaemeliyanov.mx_frontend.R
import com.ilyaemeliyanov.mx_frontend.ui.screens.DashboardScreen
import com.ilyaemeliyanov.mx_frontend.ui.screens.SettingsScreen
import com.ilyaemeliyanov.mx_frontend.ui.screens.TransactionsScreen
import com.ilyaemeliyanov.mx_frontend.ui.screens.WalletsScreen
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXColors
import com.ilyaemeliyanov.mx_frontend.viewmodel.MXAuthViewModel
import com.ilyaemeliyanov.mx_frontend.viewmodel.MXViewModel
import kotlinx.coroutines.delay

enum class Screens(@StringRes val title: Int) {
    DashboardScreen(title = R.string.dashboard_screen_route),
    WalletsScreen(title = R.string.wallets_screen_route),
    TransactionsScreen(title = R.string.transactions_screen_route),
    SettingsScreen(title = R.string.settings_screen_route),
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    BottomNavigation(
        modifier = Modifier
            .height(80.dp)
        ,
        backgroundColor = MXColors.Default.BgColor
    ) {
        val items = BottomNavigationItem().bottomNavigationItems()
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        var navSelectedItem = items.indexOfFirst { it.route == currentRoute }.takeIf { it != -1 } ?: 0

        items.forEachIndexed { index, navigationItem ->
            NavigationBarItem(
                selected = index == navSelectedItem,
                label = {
                    Text(
                        navigationItem.label,
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                icon = {
                    Icon(
                        navigationItem.icon,
                        contentDescription = navigationItem.label
                    )
                },
                onClick = {
                    navSelectedItem = index
                    navController.navigate(navigationItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

data class BottomNavigationItem(
    val label: String = "",
    val icon: ImageVector = Icons.Filled.Home,
    val route: String = ""
) {
    fun bottomNavigationItems() : List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Dashboard",
                icon = Icons.Filled.Home,
                route = Screens.DashboardScreen.name
            ),
            BottomNavigationItem(
                label = "Wallets",
                icon = Icons.Filled.CreditCard,
                route = Screens.WalletsScreen.name
            ),
            BottomNavigationItem(
                label = "Transactions",
                icon = Icons.AutoMirrored.Default.CompareArrows,
                route = Screens.TransactionsScreen.name
            ),
            BottomNavigationItem(
                label = "Settings",
                icon = Icons.Filled.Settings,
                route = Screens.SettingsScreen.name
            ),
        )
    }
}

@Composable
fun MXApp(
    mxViewModel: MXViewModel,
    mxAuthViewModel: MXAuthViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val bottomNavController = rememberNavController()
    val uiState = mxViewModel.uiState.collectAsState()

    var isLoading by remember { mutableStateOf(true) }
    LaunchedEffect(mxViewModel.transactions.isNotEmpty()) {// waiting for the transactions to load from Firestore
        if (mxViewModel.transactions.isEmpty()) {
            delay(5000) // max waiting time
        }
        isLoading = false
    }

    LaunchedEffect(Unit) {
        // If this screen is the first one to be loaded during development, remember to set mxViewModel.email
        mxViewModel.loadData(mxViewModel.email)
    }

    Column(
        modifier = modifier
    ) {
        Box(modifier = Modifier.weight(1f)) {
            NavHost(
                navController = bottomNavController,
                startDestination = Screens.DashboardScreen.name,
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                composable(Screens.DashboardScreen.name) {
                    DashboardScreen(
                        mxViewModel = mxViewModel,
                        isLoading = isLoading
                    )
                }
                composable(Screens.WalletsScreen.name) {
                    WalletsScreen(
                        mxViewModel = mxViewModel
                    )
                }
                composable(Screens.TransactionsScreen.name) {
                    TransactionsScreen(
                        mxViewModel = mxViewModel,
                        uiState = uiState.value,
                        isLoading = isLoading
                    )
                }
                composable(Screens.SettingsScreen.name) {
                    SettingsScreen(
                        mxViewModel = mxViewModel,
                        mxAuthViewModel = mxAuthViewModel,
                        navController = navController
                    )
                }
            }
        }
        BottomNavigationBar(navController = bottomNavController)
    }
}