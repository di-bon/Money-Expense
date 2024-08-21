package com.ilyaemeliyanov.mx_frontend.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXColors
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXTheme

/*
    TODO: fix me
        use DropdownMenu and DropdownMenuItem!
*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MXDropdownMenu (
    items: List<String>,
    selectedItem: String?,
    modifier: Modifier = Modifier,
    label: String = "Item",
    showIcon: Boolean = true,
    showLabel: Boolean = true,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf(selectedItem) }

    Row(modifier = modifier) {
        Box(
            modifier = Modifier
                .pointerInput(true) {
                    detectTapGestures(
                        onPress = {
                            expanded = true
                        }
                    )
                }
        )
        {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                val textToShow = when (showLabel) {
                    true -> "$label: $selected"
                    false -> "$selected"
                }
                Text(
                    text = textToShow,
//                    text = "$label: $selected" ?: "Select ${label.lowercase()}",
                    style = MaterialTheme.typography.labelMedium,
                )
                if (showIcon) {
                    Icon(
                        Icons.Filled.ArrowDropDown,
                        contentDescription = null,
                    )
                }
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(Color.White)
                .width(200.dp)
                .padding(8.dp)
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = { expanded = false; selected = item; onItemSelected(item) })
                Divider()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MXDropdownMenuPreview() {
    MXTheme {
        var currentItem by remember {
            mutableStateOf("1")
        }

        MXCard(
            containerColor = MXColors.Default.ActiveColor,
            contentColor = Color.Black,
            modifier = Modifier.padding(24.dp)
        ) {
            MXDropdownMenu(
                label = "Test",
                items = listOf(
                    "1",
                    "2",
                    "3"
                ),
                selectedItem = currentItem
            ) {
                currentItem = it
            }
        }
    }
}