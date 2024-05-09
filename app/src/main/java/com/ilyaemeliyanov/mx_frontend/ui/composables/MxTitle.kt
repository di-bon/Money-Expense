package com.ilyaemeliyanov.mx_frontend.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXTheme

@Composable
fun MxTitle(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge
        )
        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun MxTitlePreview() {
    MXTheme {
        MxTitle(
            title = "Transactions",
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            MxCircluarButton(
                onClick = {},
                modifier = Modifier
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
}