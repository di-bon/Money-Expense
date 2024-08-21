package com.ilyaemeliyanov.mx_frontend.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXColors
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXTheme

@Composable
fun MXRectangularButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = MXColors.Default.PrimaryColor,
    contentColor: Color = Color.White,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable () -> Unit
) {
    Button(
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        contentPadding = contentPadding,
        modifier = modifier
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun MXRectangularButtonPreview() {
    MXTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MXColors.Default.BgColor)
                .padding(12.dp)
        ) {
            MXRectangularButton(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(4.dp)
            ) {
                Text(
                    text = "Text",
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }
    }
}