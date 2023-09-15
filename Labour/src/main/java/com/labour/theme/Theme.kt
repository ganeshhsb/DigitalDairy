package com.ganesh.compose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = lightColorScheme(
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFf4f5f8),
    onPrimary = Color(0xFFFFFFFF),
    onSecondary = Color(0xFF212121),
    onTertiary = Color(0xFF757575),
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    primary = Color(0xFF3880ff),
    secondary = Color(0xFF009688),
    tertiary = Color(0xFF5260ff)
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
    onSecondary = Color(0xFF212121),
    onTertiary = Color(0xFF757575),
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    primary = Color(0xFF3880ff),
    secondary = Color(0xFF009688),
    tertiary = Color(0xFF5260ff)
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