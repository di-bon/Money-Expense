package com.ilyaemeliyanov.barmanager.ui.theme

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.CompareArrows
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.ilyaemeliyanov.mx_frontend.viewmodel.MXRepository
import com.ilyaemeliyanov.mx_frontend.ui.screens.DashboardScreen
import com.ilyaemeliyanov.mx_frontend.ui.screens.SettingsScreen
import com.ilyaemeliyanov.mx_frontend.ui.screens.TransactionsScreen
import com.ilyaemeliyanov.mx_frontend.ui.screens.WalletsScreen
import com.ilyaemeliyanov.mx_frontend.viewmodel.MXViewModel
import com.ilyaemeliyanov.mx_frontend.viewmodel.MXViewModelFactory
import com.ilyaemeliyanov.mx_frontend.viewmodel.MXViewModelSingleton

sealed class Screens(val route: String) {
    object Dashboard: Screens("dashboard_route")
    object Wallets: Screens("wallets_route")
    object Transactions: Screens("transactions_route")
    object Settings: Screens("settings_route")
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
                route = Screens.Dashboard.route
            ),
            BottomNavigationItem(
                label = "Wallets",
                icon = Icons.Filled.CreditCard,
                route = Screens.Wallets.route
            ),
            BottomNavigationItem(
                label = "Transactions",
                icon = Icons.AutoMirrored.Default.CompareArrows,
                route = Screens.Transactions.route
            ),
            BottomNavigationItem(
                label = "Settings",
                icon = Icons.Filled.Settings,
                route = Screens.Settings.route
            ),
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MXApp() {
    var navSelectedItem by remember { mutableStateOf(0) }
    val navController = rememberNavController()

    val mxViewModel: MXViewModel = remember { MXViewModelSingleton.getInstance() }
    LaunchedEffect(Unit) {
        val email = "john.doe@gmail.com"
        mxViewModel.loadData(email)
        Log.d("MXAPP", mxViewModel.user.toString())
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                //getting the list of bottom navigation items for our data class
                BottomNavigationItem().bottomNavigationItems().forEachIndexed {index,navigationItem ->

                    //iterating all items with their respective indexes
                    NavigationBarItem(
                        selected = index == navSelectedItem,
                        label = {
                            Text(navigationItem.label, style = MaterialTheme.typography.labelSmall)
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
    ) { innerPadding ->
        //We need to setup our NavHost in here
        NavHost(
            navController = navController,
            startDestination = Screens.Dashboard.route,
            modifier = Modifier.padding(vertical = 20.dp, horizontal = 20.dp)) {
            composable(Screens.Dashboard.route) {
                //call our composable screens here
                DashboardScreen(
                    modifier = Modifier.padding(innerPadding),
                    navController = navController
                )
            }
            composable(Screens.Wallets.route) {
                //call our composable screens here
                WalletsScreen(
                    modifier = Modifier.padding(innerPadding),
                    navController = navController
                )
            }
            composable(Screens.Transactions.route) {
                // call our composable screens here
                TransactionsScreen(
                    modifier = Modifier.padding(innerPadding),
//                    navController = navController
                )
            }
            composable(Screens.Settings.route) {
                //call our composable screens here
                SettingsScreen(
                    modifier = Modifier.padding(innerPadding),
                    navController = navController
                )
            }
        }

    }
}