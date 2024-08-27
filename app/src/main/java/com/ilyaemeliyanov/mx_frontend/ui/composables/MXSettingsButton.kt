package com.ilyaemeliyanov.mx_frontend.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXColors
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXTheme
import com.ilyaemeliyanov.mx_frontend.ui.theme.euclidCircularA

@Composable
fun MXSettingsButton(
    onClick: () -> Unit,
    leftIconImageVector: ImageVector,
    titleString: String,
    descriptionString: String,
    rightIconImageVector: ImageVector,
    modifier: Modifier = Modifier,
    titleColor: Color = Color.Unspecified
) {
    MXRectangularButton(
        onClick = onClick,
        containerColor = Color.White,
        contentColor = Color.Black,
        contentPadding = PaddingValues(),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                imageVector = leftIconImageVector,
                contentDescription = null,
                modifier = Modifier
                    .size(28.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = titleString,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = titleColor
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = descriptionString,
                    style = TextStyle(
                        fontFamily = euclidCircularA,
                        fontWeight = FontWeight.Light,
                        color = MXColors.Default.SecondaryColor
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                imageVector = rightIconImageVector,
                contentDescription = null,
                modifier = Modifier
                    .size(28.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MXSettingsButtonPreview() {
    MXTheme {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MXColors.Default.BgColor)
                .padding(12.dp)
        ) {
            MXSettingsButton(
                onClick = {},
                leftIconImageVector = Icons.Outlined.Person,
                titleString = "Change personal info",
                descriptionString = "Here you can change your firstname, lastname and password",
                rightIconImageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(88.dp)
            )
        }
    }
}