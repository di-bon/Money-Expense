package com.ilyaemeliyanov.mx_frontend.ui.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXColors
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXTheme

@Composable
fun MxCircluarButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = MXColors.Default.primaryColor,
    contentColor: Color = Color.White,
    content: @Composable () -> Unit
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        shape = CircleShape,
        contentPadding = PaddingValues(0.dp),
        onClick = onClick,
        modifier = modifier
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun MxCircularButtonPreview() {
    MXTheme {

        MxCircluarButton(
            onClick = {},
            modifier = Modifier
                .padding(8.dp)
                .size(32.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}