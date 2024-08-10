package com.ilyaemeliyanov.mx_frontend.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXColors
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXTheme

@Composable
fun MXPopup(
    titleString: String,
    modifier: Modifier = Modifier,
    contentPadding: Dp = 8.dp,
    content: @Composable () -> Unit
) {
    Popup(alignment = Alignment.Center) {
        MXCard (
            containerColor = Color.White,
            modifier = modifier.padding(16.dp)
        ) {
            LazyColumn (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(contentPadding)
            ) {
                item {
                    Text(
                        text = titleString,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                item {
                    content()
                }
            }
        }
    }

}

//@Preview(showBackground = true)
//@Composable
//private fun MXPopupPreview() {
//    MXTheme {
//        Column (
//            modifier = Modifier
//                .fillMaxWidth()
//                .fillMaxHeight()
//                .background(color = MXColors.Default.BgColor)
//        ) {
//            MXPopup(
//                titleString = "Create Wallet",
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(24.dp)
//            ) {
//                Spacer(modifier = Modifier.height(8.dp))
//                MXInput(
//                    titleText = "Name",
//                    labelText = "Enter your wallet name...",
//                    modifier = Modifier.padding(vertical = 8.dp)
//                )
//                MXInput(
//                    titleText = "Description",
//                    labelText = "Enter a short description for your wallet...",
//                    modifier = Modifier.padding(vertical = 8.dp)
//                )
//                MXInput(
//                    titleText = "Amount",
//                    labelText = "Enter the initial amount...",
//                    modifier = Modifier.padding(vertical = 8.dp)
//                )
//                Spacer(modifier = Modifier.height(16.dp))
//                Row(modifier = Modifier.padding(vertical = 8.dp)) {
//                    MXRectangularButton(
//                        onClick = { /*TODO*/ },
//                        containerColor = MXColors.Default.PrimaryColor,
//                        contentColor = Color.White,
//                        modifier = Modifier.weight(1f)
//                    ) {
//                        Text("Cancel")
//                    }
//                    Spacer(modifier = Modifier.width(24.dp))
//                    MXRectangularButton(
//                        onClick = { /*TODO*/ },
//                        containerColor = MXColors.Default.ActiveColor,
//                        contentColor = Color.Black,
//                        modifier = Modifier.weight(1f)
//                    ) {
//                        Text("Create")
//                    }
//                }
//            }
//        }
//    }
//}