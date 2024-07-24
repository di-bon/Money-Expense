package com.ilyaemeliyanov.mx_frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilyaemeliyanov.mx_frontend.ui.MxApp
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXColors
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXIcons
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXTheme

// TODO: merge this class with ui/MxApp.kt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MXTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MXColors.Default.bgColor
                ) {
                    MxApp(modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
 }

@Preview(showBackground = true)
@Composable
fun MoneyExpensePreview() {
    MXTheme {
        MxApp(modifier = Modifier.padding(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun MXScreenPreview() {
    MXTheme {
        MXScreen()
    }
}

@Composable
fun MXScreen() {
    Scaffold(
        bottomBar = {
            BottomNavigationBar()
        }
    ) { padding ->
        Column(modifier=Modifier.padding(padding)) {
            Text("Dashboard")
        }
    }
}

// Bottom navbar
data class NavbarItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)
@Composable
fun BottomNavigationBar() {
    NavigationBar(
        containerColor = MXColors.Navbar.navbarBgColor,
        contentColor = Color.White
    ) {

        val navBarItems = listOf(
            NavbarItem("/dashboard", "Dashboard", ImageVector.vectorResource(MXIcons.Default.dashboardIconId)),
            NavbarItem("/wallets", "Wallets", ImageVector.vectorResource(MXIcons.Default.walletsIconId)),
            NavbarItem("/transactions", "Transactions", ImageVector.vectorResource(MXIcons.Default.transactionsIconId)),
            NavbarItem("/settings", "Settings", ImageVector.vectorResource(MXIcons.Default.settingsIconId))
        )
        var selectedIndex by remember { mutableIntStateOf(0) }

        navBarItems.forEachIndexed { index, item ->
            NavigationBarItem (
                selected = selectedIndex == index,
                onClick = {
                      selectedIndex = index
                },
                icon = { Icon(item.icon, tint=(if (selectedIndex == index) MXColors.Navbar.iconSelectedColor else MXColors.Navbar.iconDefaultColor), contentDescription = null, modifier=Modifier.padding(10.dp)) },
                label = { Text(item.label, color=MXColors.Default.secondaryColor) }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MXTheme {
        BottomNavigationBar()
    }
}