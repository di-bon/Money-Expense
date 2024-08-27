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
import androidx.compose.material3.AlertDialog
//import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXColors
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXTheme
import com.ilyaemeliyanov.mx_frontend.ui.theme.spaceGrotesk

//@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MXAlertDialog(
    title: String,
    dismissLabel: String,
    confirmLabel: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    confirmLabelColor: Color = Color.Black,
    confirmContainerColor: Color = MXColors.Default.ActiveColor,
    content: @Composable () -> Unit
) {
    AlertDialog(
        modifier = Modifier.fillMaxWidth(),
        onDismissRequest = onDismiss,
        title = { Text(title, style = TextStyle(fontFamily = spaceGrotesk, fontSize = 30.sp, fontWeight = FontWeight.Normal)) },
        text = {
            MXCard (
                containerColor = Color.White,
            ) {
                LazyColumn (
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    item {
                        content()
                    }
                }
            }
        },
        confirmButton = {
            MXRectangularButton(
                onClick = onConfirm,
                containerColor = confirmContainerColor,
                contentColor = confirmLabelColor,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(confirmLabel)
            }
        },
        dismissButton = {
            MXRectangularButton(
                onClick = onDismiss,
                containerColor = MXColors.Default.PrimaryColor,
                contentColor = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(dismissLabel)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun MXAlertDialogPreview() {
    MXTheme {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = MXColors.Default.BgColor)
        ) {
            MXAlertDialog(
                title = "Create Wallet",
                dismissLabel = "",
                confirmLabel = "",
                onDismiss = {},
                onConfirm = {},
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                MXInput(
                    titleText = "Name",
                    labelText = "Enter your wallet name...",
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = "",
                    onTextChange = { }
                )
                MXInput(
                    titleText = "Description",
                    labelText = "Enter a short description for your wallet...",
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = "",
                    onTextChange = { }
                )
                MXInput(
                    titleText = "Amount",
                    labelText = "Enter the initial amount...",
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = "",
                    onTextChange = { }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.padding(vertical = 8.dp)) {
                    MXRectangularButton(
                        onClick = {},
                        containerColor = MXColors.Default.PrimaryColor,
                        contentColor = Color.White,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(24.dp))
                    MXRectangularButton(
                        onClick = {},
                        containerColor = MXColors.Default.ActiveColor,
                        contentColor = Color.Black,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Create")
                    }
                }
            }
        }
    }
}