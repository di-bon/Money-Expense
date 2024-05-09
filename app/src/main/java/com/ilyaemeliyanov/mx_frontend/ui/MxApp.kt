package com.ilyaemeliyanov.mx_frontend.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ilyaemeliyanov.mx_frontend.R
import com.ilyaemeliyanov.mx_frontend.ui.screens.DashboardScreen
import com.ilyaemeliyanov.mx_frontend.ui.screens.InitialScreen
import com.ilyaemeliyanov.mx_frontend.ui.theme.MxfrontendTheme

enum class MxScreen(@StringRes val title: Int) {
    Initial(title = R.string.initial_screen),
    Dashboard(title = R.string.dashboard_screen)
}

@Composable
fun MxApp(
    navController: NavHostController = rememberNavController(),
    viewModel: MxViewModel = viewModel(),
    modifier: Modifier = Modifier
) {

    Scaffold { innerPadding ->
        val uiState = viewModel.uiState.collectAsState().value

        NavHost(
            navController = navController,
            startDestination = MxScreen.Initial.name,
            modifier = Modifier
                .padding(innerPadding)
        ) {
            composable(route = MxScreen.Initial.name) {
                InitialScreen(
                    onLoginClick = {
                        /* TODO */
                        if (viewModel.checkLogIn()) {
                            navController.navigate(MxScreen.Dashboard.name)
                        }
                    },
                    onSignUpClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(24.dp)
                )
            }

            composable(route = MxScreen.Dashboard.name) {
                DashboardScreen(
                    modifier = Modifier.padding(24.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MxAppPreview() {
    MxfrontendTheme {
        MxApp(modifier = Modifier.padding(16.dp))
    }
}