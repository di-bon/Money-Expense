package com.ilyaemeliyanov.barmanager.ui.theme

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.BottomNavigation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.CompareArrows
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
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
import com.ilyaemeliyanov.mx_frontend.ui.screens.DashboardScreen
import com.ilyaemeliyanov.mx_frontend.ui.screens.SettingsScreen
import com.ilyaemeliyanov.mx_frontend.ui.screens.TransactionsScreen
import com.ilyaemeliyanov.mx_frontend.ui.screens.WalletsScreen
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXColors
import com.ilyaemeliyanov.mx_frontend.viewmodel.MXViewModel
import kotlinx.coroutines.delay

private const val TAG = "MXApp"

sealed class Screens(val route: String) {
    object Dashboard: Screens("dashboard_route")
    object Wallets: Screens("wallets_route")
    object Transactions: Screens("transactions_route")
    object Settings: Screens("settings_route")
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    BottomNavigation(
        modifier = Modifier
            .height(80.dp)
//            .height(64.dp)
//            .padding(vertical = 4.dp)
        ,
        backgroundColor = MXColors.Default.BgColor
    ) {
        val items = BottomNavigationItem().bottomNavigationItems()
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        var navSelectedItem by remember { mutableStateOf(0) }

        items.forEachIndexed { index, navigationItem ->
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

//        items.forEachIndexed { index, screen ->
//            NavigationBarItem(
//                icon = {
//                    Icon(
//                        screen.icon,
//                        contentDescription = screen.label
//                    )
//                },
//                label = { Text(screen.label) },
//                selected = currentRoute == screen,
////                onClick = {
////                    if (currentRoute != screen) {
////                        navController.navigate(screen) {
////                            // Avoid multiple copies of the same destination
////                            popUpTo(navController.graph.startDestinationId) {
////                                saveState = true
////                            }
////                            // Avoid building up a large stack of destinations
////                            launchSingleTop = true
////                            restoreState = true
////                        }
////                    }
////                }
//                onClick = {
//                    navSelectedItem = index
//                    bottomNavController.navigate(navigationItem.route) {
//                        popUpTo(bottomNavController.graph.findStartDestination().id) {
//                            saveState = true
//                        }
//                        launchSingleTop = true
//                        restoreState = true
//                    }
//                }
//            )
//        }
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

@Composable
fun MXApp(
//    navController: NavHostController,
    mxViewModel: MXViewModel,
    modifier: Modifier = Modifier
) {
    Log.d(TAG, "Made it to MXApp")

    var navSelectedItem by remember { mutableStateOf(0) }
    val bottomNavController = rememberNavController()

    var isLoading by remember {
        mutableStateOf(true)
    }
    LaunchedEffect(mxViewModel.transactions.isNotEmpty()) {// waiting for the transactions to load from Firestore
        if (mxViewModel.transactions.isEmpty()) {
            delay(10000) // set max of 10000 ms
        }
        isLoading = false
    }

//    val mxViewModel: MXViewModel = remember { MXViewModelSingleton.getInstance() }
//    val mxViewModel: MXViewModel = viewModel()
    LaunchedEffect(Unit) {
//        val email = "john.doe@gmail.com"
//        mxViewModel.email = "dibon.francesco@gmail.com"
        mxViewModel.email = "john.doe@gmail.com"
        mxViewModel.loadData(mxViewModel.email)
        isLoading = false
        Log.d(TAG, mxViewModel.user.toString())
    }

    Column(
        modifier = modifier
    ) {
        // Main content
        Box(modifier = Modifier.weight(1f)) {
            NavHost(
                navController = bottomNavController,
                startDestination = Screens.Dashboard.route,
                modifier = Modifier
//                .padding(vertical = 20.dp, horizontal = 20.dp)
                    .fillMaxHeight()
            ) {
                composable(Screens.Dashboard.route) {
                    //call our composable screens here
                    DashboardScreen(
                        mxViewModel = mxViewModel,
                        uiState = mxViewModel.uiState.collectAsState().value,
                        isLoading = isLoading,
                        modifier = Modifier
                            .padding(32.dp)
                    )
                }
                composable(Screens.Wallets.route) {
                    //call our composable screens here
                    WalletsScreen(
                        modifier = Modifier
                            .padding(32.dp)
                    )
                }
                composable(Screens.Transactions.route) {
                    // call our composable screens here
                    TransactionsScreen(
//                    getFilteredAndSortedTransactions = mxViewModel::getFilteredAndSortedTransactions,
//                    getBalance = mxViewModel::getBalance,
                        uiState = mxViewModel.uiState.collectAsState().value,
                        mxViewModel = mxViewModel,
                        isLoading = isLoading,
                        modifier = Modifier
                            .padding(32.dp)
                            .fillMaxHeight()
                            .fillMaxWidth()
                    )
                }
                composable(Screens.Settings.route) {
                    //call our composable screens here
                    SettingsScreen(
                        modifier = Modifier
                            .padding(32.dp)
                    )
                }
            }
        }
        // Bottom Navigation
        BottomNavigationBar(navController = bottomNavController)
    }

//    Scaffold(
//        contentWindowInsets = WindowInsets(0.dp),
//        modifier = modifier,
//        bottomBar = {
//            NavigationBar {
//                //getting the list of bottom navigation items for our data class
//                BottomNavigationItem().bottomNavigationItems().forEachIndexed { index, navigationItem ->
//                    //iterating all items with their respective indexes
//                    NavigationBarItem(
//                        selected = index == navSelectedItem,
//                        label = {
//                            Text(navigationItem.label, style = MaterialTheme.typography.labelSmall)
//                        },
//                        icon = {
//                            Icon(
//                                navigationItem.icon,
//                                contentDescription = navigationItem.label
//                            )
//                        },
//                        onClick = {
//                            navSelectedItem = index
//                            bottomNavController.navigate(navigationItem.route) {
//                                popUpTo(bottomNavController.graph.findStartDestination().id) {
//                                    saveState = true
//                                }
//                                launchSingleTop = true
//                                restoreState = true
//                            }
//                        }
//                    )
//                }
//            }
//        }
//    ) { innerPadding ->
////        We need to setup our NavHost in here
//        NavHost(
//            navController = bottomNavController,
//            startDestination = Screens.Dashboard.route,
//            modifier = Modifier
//                .padding(innerPadding)
////                .padding(vertical = 20.dp, horizontal = 20.dp)
//                .fillMaxHeight()
//        ) {
//            composable(Screens.Dashboard.route) {
//                //call our composable screens here
//                DashboardScreen(
//                    mxViewModel = mxViewModel,
//                    uiState = mxViewModel.uiState.collectAsState().value,
//                    modifier = Modifier.padding(innerPadding),
//                )
//            }
//            composable(Screens.Wallets.route) {
//                //call our composable screens here
//                WalletsScreen(
//                    modifier = Modifier.padding(innerPadding),
//                )
//            }
//            composable(Screens.Transactions.route) {
//                // call our composable screens here
//                TransactionsScreen(
////                    getFilteredAndSortedTransactions = mxViewModel::getFilteredAndSortedTransactions,
////                    getBalance = mxViewModel::getBalance,
//                    uiState = mxViewModel.uiState.collectAsState().value,
//                    mxViewModel = mxViewModel,
//                    modifier = Modifier
//                        .fillMaxHeight()
//                        .fillMaxWidth()
//                        .padding(innerPadding),
//                )
//            }
//            composable(Screens.Settings.route) {
//                //call our composable screens here
//                SettingsScreen(
//                    modifier = Modifier.padding(innerPadding),
//                )
//            }
//        }
//    }
}