package com.ilyaemeliyanov.mx_frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.ilyaemeliyanov.mx_frontend.ui.MXApp
import com.ilyaemeliyanov.mx_frontend.ui.screens.InitialScreen
import com.ilyaemeliyanov.mx_frontend.ui.screens.LoginScreen
import com.ilyaemeliyanov.mx_frontend.ui.screens.SignUpScreen
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXColors
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXTheme
import com.ilyaemeliyanov.mx_frontend.viewmodel.MXAuthViewModel
import com.ilyaemeliyanov.mx_frontend.viewmodel.MXRepository
import com.ilyaemeliyanov.mx_frontend.viewmodel.MXViewModel

enum class AuthScreens(@StringRes val title: Int) {
    InitialScreen(title = R.string.initial_screen_route),
    LoginScreen(title = R.string.login_screen_route),
    SignUpScreen(title = R.string.signup_screen_route),
    MXApp(title = R.string.mx_app_route)
}

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private var isUserAlreadyLoggedIn = false

    private  val mxAuthViewModel = MXAuthViewModel()
    private val mxViewModel = MXViewModel(MXRepository())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth

        setContent {
            MXTheme {
                val navController: NavHostController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination =
                    if (isUserAlreadyLoggedIn)
                        AuthScreens.MXApp.name
                    else
                        AuthScreens.InitialScreen.name,
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
                            mxViewModel = mxViewModel,
                            modifier = Modifier
                                .background(color = MXColors.Default.BgColor)
                                .padding(32.dp)
                                .fillMaxWidth()
                                .fillMaxHeight()
                        )
                    }
                    composable(route = AuthScreens.MXApp.name) {
                        MXApp(
                            mxViewModel = mxViewModel,
                            mxAuthViewModel = mxAuthViewModel,
                            navController = navController,
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
        val currentUser = auth.currentUser
        if (currentUser != null && mxAuthViewModel.user != null && mxAuthViewModel.user?.email != null) {
            mxViewModel.email = mxAuthViewModel.user!!.email.toString()
            isUserAlreadyLoggedIn = true
        }
    }
}