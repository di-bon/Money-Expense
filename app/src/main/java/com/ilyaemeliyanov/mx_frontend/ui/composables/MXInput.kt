package com.ilyaemeliyanov.mx_frontend.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXTheme
import com.ilyaemeliyanov.mx_frontend.ui.theme.euclidCircularA

@Composable
fun MXInput(
    titleText: String,
    labelText: String,
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
    isError: Boolean = false,
    errorMessage: String = ""
) {
    Column(modifier = modifier) {
        Text(text = titleText)
        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            label = { Text(
                text = labelText,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            ) },
            keyboardOptions = keyboardOptions,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            isError = isError
        )
        if (isError) {
            Text(
                text = errorMessage,
                style = TextStyle(
                    fontFamily = euclidCircularA,
                    fontWeight = FontWeight.Light,
                    fontSize = 16.sp),
                color = Color(red = 162, green = 21, blue = 23)
            )
        }
    }
}

@Composable
fun MXSecretInput(
    titleText: String,
    labelText: String,
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
    isError: Boolean = false,
    errorMessage: String = ""
) {
    var secretVisible by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Text(text = titleText)
        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            label = {
                Text(
                    text = labelText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            visualTransformation = if (secretVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = keyboardOptions,
            trailingIcon = {
                val image = if (secretVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff

                val description = if (secretVisible) "Hide password" else "Show password"

                IconButton(onClick = { secretVisible = !secretVisible }) {
                    Icon(imageVector = image, contentDescription = description)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            isError = isError
        )
        if (isError) {
            Text(
                text = errorMessage,
                style = TextStyle(
                    fontFamily = euclidCircularA,
                    fontWeight = FontWeight.Light,
                    fontSize = 16.sp
                ),
                color = Color(red = 162, green = 21, blue = 23)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun MXInputPreview() {
    MXTheme {
        Column (
            modifier = Modifier
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