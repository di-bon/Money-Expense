package com.ilyaemeliyanov.mx_frontend.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilyaemeliyanov.mx_frontend.data.wallets.Wallet
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXTheme
import com.ilyaemeliyanov.mx_frontend.utils.StringFormatter

@Composable
fun MXWallet(
    wallet: Wallet,
    cardColor: Color,
    modifier: Modifier = Modifier
) {
    MXCard(
        containerColor = cardColor,
        contentColor = Color.Black,
        modifier = modifier
    ) {
        Column (
            verticalArrangement = Arrangement.Top,
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(IntrinsicSize.Min)
            ) {
                Text(text = wallet.name, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.weight(1f))
//                Surface(
//                    onClick = { /*TODO*/ },
//                    modifier = Modifier
//                        .fillMaxHeight(),
//                    color = Color.Transparent
//                ) {
//                    Icon(
//                        imageVector = Icons.Filled.Edit,
//                        contentDescription = "Edit wallet",
//
//                    )
//                }
//                Surface(
//                    onClick = { /*TODO*/ },
//                    modifier = Modifier
//                        .fillMaxHeight(),
//                    color = Color.Transparent,
//                    ) {
//                    Icon(imageVector = Icons.Filled.Delete, contentDescription = "Edit wallet")
//                }
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit wallet",
                    modifier = Modifier
                        .fillMaxHeight()
                        .clickable { /*TODO*/ }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Edit wallet",
                    modifier = Modifier
                        .fillMaxHeight()
                        .clickable { /*TODO*/ }
                )
            }
            Text(
                text = StringFormatter.getFormattedAmount(wallet.amount),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .height(IntrinsicSize.Min)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = wallet.description.toString(),
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun MXWalletPreview() {
//    MXTheme {
//        MXWallet(
//            wallet = Wallet(
//                name = "PayPal",
//                amount = 1234.00f,
//                description = "Wallet for tracing transactions from and to my personal paypal account"
//            ),
//            cardColor = Color(red = 105, green = 247, blue = 179),
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(IntrinsicSize.Min)
//                .padding(32.dp)
//        )
//    }
//}