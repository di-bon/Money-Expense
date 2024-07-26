package com.ilyaemeliyanov.mx_frontend.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilyaemeliyanov.mx_frontend.ui.composables.MxCard
import com.ilyaemeliyanov.mx_frontend.ui.composables.MxCircluarButton
import com.ilyaemeliyanov.mx_frontend.ui.composables.MxTitle
import com.ilyaemeliyanov.mx_frontend.ui.composables.RecentTransactions
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXColors
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXTheme

private const val TAG = "TransactionsScreen"

@Composable
fun TransactionsScreen(modifier: Modifier = Modifier) {
    Column (
        modifier = modifier
    ) {
        MxTitle(title = "Transactions", modifier = Modifier.fillMaxWidth()) {
            MxCircluarButton(
                onClick = {/* TODO */},
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

        Spacer(modifier = Modifier.height(8.dp))

        Row (
            modifier = Modifier.fillMaxWidth()
        ) {
            MxCircluarButton(
                onClick = { /*TODO*/ },
                containerColor = MXColors.Default.activeColor,
                contentColor = Color.Black,
                modifier = Modifier.weight(1f)
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Text(text = "Filer by")
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(imageVector = Icons.Filled.KeyboardArrowDown, contentDescription = "Filter By")
                }
            }
            Spacer(modifier = Modifier.width(24.dp))
            MxCircluarButton(
                onClick = { /*TODO*/ },
                containerColor = MXColors.Default.activeColor,
                contentColor = Color.Black,
                modifier = Modifier.weight(1f)
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Text(text = "Sort by")
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(imageVector = Icons.Filled.KeyboardArrowDown, contentDescription = "Filter By")
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(8.dp))
                .background(color = Color.Black)
        ) {
            Text(
                text = "Sum: ",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        MxCard(
            containerColor = Color.White
        ) {
            RecentTransactions(
                showTitle = false
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TransactionsScreenPreview() {
    MXTheme {
        TransactionsScreen(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = MXColors.Default.bgColor)
                .padding(16.dp)
        )
    }
}