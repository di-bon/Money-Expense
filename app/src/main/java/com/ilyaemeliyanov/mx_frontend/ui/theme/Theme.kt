package com.ilyaemeliyanov.mx_frontend.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.ilyaemeliyanov.mx_frontend.R

private val DarkColorScheme = darkColorScheme(
    primary = MXColors.Default.PrimaryColor,
    secondary = MXColors.Default.SecondaryColor,
    tertiary = MXColors.Default.ActiveColor,
)

private val LightColorScheme = lightColorScheme(
    primary = MXColors.Default.PrimaryColor,
    secondary = MXColors.Default.SecondaryColor,
    tertiary = MXColors.Default.ActiveColor,

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

val euclidCircularA = FontFamily(
    Font(R.font.euclid_circular_a_regular, FontWeight.Normal),
    Font(R.font.euclid_circular_a_regular_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.euclid_circular_a_medium, FontWeight.Medium),
    Font(R.font.euclid_circular_a_medium_italic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.euclid_circular_a_semibold, FontWeight.SemiBold),
    Font(R.font.euclid_circular_a_medium_italic, FontWeight.SemiBold, FontStyle.Italic),
    Font(R.font.euclid_circular_a_bold, FontWeight.Bold),
    Font(R.font.euclid_circular_a_bold_italic, FontWeight.Bold, FontStyle.Italic),
)

val spaceGrotesk = FontFamily(
    Font(R.font.space_grotesk_light, FontWeight.Light),
    Font(R.font.space_grotesk_regular, FontWeight.Normal),
    Font(R.font.space_grotesk_medium, FontWeight.Medium),
    Font(R.font.space_grotesk_semibold, FontWeight.SemiBold),
    Font(R.font.space_grotesk_bold, FontWeight.Bold),
)

private val MXTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = euclidCircularA,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = euclidCircularA,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = euclidCircularA,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = euclidCircularA,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp, // obliged to use 24.sp and not 64.sp because otherwise date picker title too large
    ),
    titleMedium = TextStyle(
        fontFamily = euclidCircularA,
        fontWeight = FontWeight.Normal,
        fontSize = 30.sp
    ),
    titleSmall = TextStyle(
        fontFamily = euclidCircularA,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    labelLarge = TextStyle(
        fontFamily = spaceGrotesk,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    ),
    labelMedium = TextStyle(
        fontFamily = spaceGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    labelSmall = TextStyle(
        fontFamily = spaceGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = euclidCircularA,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = euclidCircularA,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodySmall = TextStyle(
        fontFamily = euclidCircularA,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
)



@Composable
fun MXTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MXTypography,
        content = content
    )
}