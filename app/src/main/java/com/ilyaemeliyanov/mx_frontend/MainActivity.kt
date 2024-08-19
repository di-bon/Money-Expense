package com.ilyaemeliyanov.mx_frontend

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import com.ilyaemeliyanov.barmanager.ui.theme.MXApp
import com.ilyaemeliyanov.mx_frontend.ui.screens.TransactionsScreen
//import com.ilyaemeliyanov.barmanager.ui.theme.MXApp
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXTheme
import com.ilyaemeliyanov.mx_frontend.viewmodel.MXRepository
import com.ilyaemeliyanov.mx_frontend.viewmodel.MXViewModel
import com.ilyaemeliyanov.mx_frontend.viewmodel.MXViewModelSingleton

// TODO: merge this class with ui/MxAppOld.kt

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MXTheme {
                val mxViewModel: MXViewModel = MXViewModel(
                    email = "john.doe@gmail.com",
                    repository = MXRepository()
                )
//                val mxViewModel: MXViewModel = MXViewModelSingleton.getInstance()
//                Log.d(TAG, "Instantiating TransactionsScreen")
//                TransactionsScreen(
//                    uiState = vm.uiState.collectAsState().value,
//                    mxViewModel = vm
//                )
//                Log.d(TAG, "TransactionsScreen instantiated.")

                MXApp(
                    mxViewModel = mxViewModel
                )

//                InitialScreen(
//                    onLoginClick = { /*TODO*/ },
//                    onSignUpClick = { /*TODO*/ },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .fillMaxHeight()
//                        .background(color = MXColors.Default.BgColor)
//                        .padding(32.dp)
//                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun MoneyExpensePreview() {
//    MXTheme {
//        MxApp(modifier = Modifier.padding(16.dp))
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun MXScreenPreview() {
//    MXTheme {
//        MXScreen()
//    }
//}