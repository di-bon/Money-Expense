package com.ilyaemeliyanov.mx_frontend.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXColors
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXTheme

@Composable
fun MXInput(
    titleText: String,
    labelText: String,
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(text = titleText)
//        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            label = { Text(
                text = labelText,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            ) },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MXInputPreview() {
    MXTheme {
        Column (
            modifier = Modifier
                .fillMaxHeight()
                .background(color = MXColors.Default.BgColor)
        ) {
            MXInput(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                titleText = "First name",
                labelText = "Enter your first name...",
                text = "",
                onTextChange = {}
            )
        }
    }
}