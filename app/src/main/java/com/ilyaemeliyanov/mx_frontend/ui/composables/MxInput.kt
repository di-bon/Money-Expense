package com.ilyaemeliyanov.mx_frontend.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXColors
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXTheme

@Composable
fun MxInput(
    titleText: String,
    labelText: String,
    modifier: Modifier = Modifier
) {
    var text by remember { mutableStateOf("") }

    Column(modifier = modifier) {
        Text(text = titleText)
//        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text(
                text = labelText,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            ) },
            modifier = Modifier
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.small
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MxInputPreview() {
    MXTheme {
        Column (
            modifier = Modifier
                .fillMaxHeight()
                .background(color = MXColors.Default.bgColor)
        ) {
            MxInput(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                titleText = "First name",
                labelText = "Enter your first name..."
            )
        }
    }
}