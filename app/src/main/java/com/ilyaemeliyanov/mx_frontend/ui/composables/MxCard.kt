package com.ilyaemeliyanov.mx_frontend.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ilyaemeliyanov.mx_frontend.data.Transaction
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXTheme
import java.util.Calendar
import java.util.GregorianCalendar

/* TODO: update this composable:
    - change content color logic: it shouldn't set every color of the composable
 */

@Composable
fun MxCard(
    modifier: Modifier = Modifier,
    containerColor: Color = Color.Transparent,
    contentColor: Color = Color.Black,
    contentPadding: Dp = 12.dp,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(contentPadding)
        ) {
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MxCardPreview() {
    MXTheme {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            MxCard(
                containerColor = Color(225, 225, 225),
                contentColor = Color.Black,
                modifier = Modifier.padding(16.dp)
            ) {
                MxTransaction(
                    transaction = Transaction(
                        title = "MyTransaction",
                        amount = 10.00f,
                        date = GregorianCalendar(2024, Calendar.APRIL, 4).time,
                        description = null
                    ),
                    showBottomDivider = false
                )
            }
        }
    }
}