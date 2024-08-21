package com.ilyaemeliyanov.mx_frontend

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.ilyaemeliyanov.barmanager.ui.theme.MXApp
import com.ilyaemeliyanov.mx_frontend.ui.screens.InitialScreen
import com.ilyaemeliyanov.mx_frontend.ui.screens.LoginScreen
import com.ilyaemeliyanov.mx_frontend.ui.screens.SignUpScreen
import com.ilyaemeliyanov.mx_frontend.ui.screens.TransactionsScreen
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXColors
//import com.ilyaemeliyanov.barmanager.ui.theme.MXApp
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXTheme
import com.ilyaemeliyanov.mx_frontend.viewmodel.MXAuthViewModel
import com.ilyaemeliyanov.mx_frontend.viewmodel.MXRepository
import com.ilyaemeliyanov.mx_frontend.viewmodel.MXViewModel
import com.ilyaemeliyanov.mx_frontend.viewmodel.MXViewModelSingleton

// TODO: merge this class with ui/MxAppOld.kt

private const val TAG = "MainActivity"

enum class AuthScreens(@StringRes val title: Int) {
    InitialScreen(title = R.string.initialscreenroute),
    LoginScreen(title = R.string.loginscreenroute),
    SignUpScreen(title = R.string.signupscreenroute),
    MXApp(title = R.string.mxapproute)
}

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase Auth
        auth = Firebase.auth

        val mxAuthViewModel = MXAuthViewModel()

        val mxViewModel = MXViewModel(MXRepository())

        setContent {
            MXTheme {
                val navController: NavHostController = rememberNavController()

                // Get current back stack entry
                val backStackEntry by navController.currentBackStackEntryAsState()
                // Get the name of the current screen
                val currentScreen = AuthScreens.valueOf(
                    backStackEntry?.destination?.route ?: AuthScreens.MXApp.name
                )

                NavHost(
                    navController = navController,
                    startDestination = AuthScreens.InitialScreen.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    composable(route = AuthScreens.InitialScreen.name) {
                        InitialScreen(
                            navController = navController,
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .background(color = MXColors.Default.BgColor)
                                .padding(32.dp)
                        )
                    }
                    composable(route = AuthScreens.LoginScreen.name) {
                        LoginScreen(
                            navController = navController,
                            mxAuthViewModel = mxAuthViewModel,
                            mxViewModel = mxViewModel,
                            modifier = Modifier
                                .background(color = MXColors.Default.BgColor)
                                .padding(32.dp)
                                .fillMaxWidth()
                                .fillMaxHeight()
                        )
                    }
                    composable(route = AuthScreens.SignUpScreen.name) {
                        SignUpScreen(
                            navController = navController,
                            mxAuthViewModel = mxAuthViewModel,
//                            navigateUp = navController::navigateUp,
//                            canNavigateBack = navController.previousBackStackEntry != null,
                            mxViewModel = mxViewModel,
                            modifier = Modifier
                                .background(color = MXColors.Default.BgColor)
                                .padding(32.dp)
                                .fillMaxWidth()
                                .fillMaxHeight()
                        )
                    }
                    composable(route = AuthScreens.MXApp.name) {
//                            TestScreen(modifier = Modifier
//                                .height(400.dp))
                        MXApp(
//                                navController = navController,
                            mxViewModel = mxViewModel,
                            modifier = Modifier
                                .background(color = MXColors.Default.BgColor)
                                .fillMaxWidth()
                                .fillMaxHeight()
                        )
                    }
                }
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // TODO: navigate to MXApp
//            reload()
        }
    }
}