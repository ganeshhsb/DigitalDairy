package com.digitaldairy.compose.appcomponents.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Light Theme Colors
val LightColorScheme = lightColorScheme(
    primary = Color(0xFF69A9E5),  // Main primary color
    onPrimary = Color.White,
    primaryContainer = Color(0xFFA9D3EC),  // Light version of primary
    onPrimaryContainer = Color(0xFF5177AC),
    secondary = Color(0xFF73B5E8),
    onSecondary = Color.White,
    background = Color(0xFFFDF6F2),  // Light background
    onBackground = Color.Black,
    surface = Color(0xFFD5E6F0),  // Surface color
    onSurface = Color.Black,
    tertiary = Color(0xFFE5A969),
            onTertiary = Color.White,
)

// Dark Theme Colors
val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF6393DA),  // Darker version of primary
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF5177AC),
    onPrimaryContainer = Color.White,
    secondary = Color(0xFF5B8AC3),
    onSecondary = Color.Black,
    background = Color(0xFF121212),  // Dark mode background
    onBackground = Color.White,
    surface = Color(0xFF43598C),
    onSurface = Color.White,
            tertiary = Color(0xFFE5A969),
    onTertiary = Color.Black,
)