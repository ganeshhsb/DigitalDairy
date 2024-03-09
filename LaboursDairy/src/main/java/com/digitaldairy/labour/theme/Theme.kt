package com.digitaldairy.labour.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = lightColorScheme(
//    background = Color(0xFFFFFFFF),
//    onBackground = Color(0x00000000),
//
//    surface = Color(0xFFFFFFFF),
//    onSurface = Color(0x00000000),
//
//    primary = Color(0xFF757ce8),
//    onPrimary = Color(0x00000000),
//
//    secondary = Color(0xFFff7961),
//    onSecondary = Color(0x00000000),
//
//    tertiary = Color(0xFF5260ff),
//    onTertiary = Color(0x00000000),
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFf4f5f8),
    onPrimary = Color(0xFF000000),
    onSecondary = Color(0xFF000000),
    onTertiary = Color(0xFF000000),
    onBackground = Color(0xFF000000),
    onSurface = Color(0xFF000000),
    primary = Color(0xFF0277BD),
    secondary = Color(0xFF4FC3F7),
    tertiary = Color(0xFFB3E5FC)
)

private val LightColorScheme = lightColorScheme(
//    background = Color(0xFFFFFBFE),
//    surface = Color(0xFFFFFBFE),
//    onPrimary = Color(0xFFFFFFFF),
//    onSecondary = Color(0xFF212121),
//    onTertiary = Color(0xFF757575),
//    onBackground = Color(0xFF1C1B1F),
//    onSurface = Color(0xFF1C1B1F),
//    primary = Color(0xFF03A9F4),
//    secondary = Color(0xFF009688),
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFf4f5f8),
    onPrimary = Color(0xFFFFFFFF),
    onSecondary = Color(0xFFFFFFFF),
    onTertiary = Color(0xFFFFFFFF),
    onBackground = Color(0xFF000000),
    onSurface = Color(0xFF000000),
    primary = Color(0xFF0277BD),
    secondary = Color(0xFF4FC3F7),
    tertiary = Color(0xFFB3E5FC)
)

@Composable
fun HelloComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}