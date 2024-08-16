package com.ilyaemeliyanov.mx_frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.ilyaemeliyanov.barmanager.ui.theme.MXApp
import com.ilyaemeliyanov.mx_frontend.ui.screens.InitialScreen
//import com.ilyaemeliyanov.barmanager.ui.theme.MXApp
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXColors
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXIcons
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXTheme

// TODO: merge this class with ui/MxAppOld.kt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MXTheme {
                MXApp()
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