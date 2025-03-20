//package com.digitaldairy.labour.theme
//
//import androidx.compose.foundation.isSystemInDarkTheme
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.darkColorScheme
//import androidx.compose.material3.lightColorScheme
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.graphics.Color
//
////val DarkColorScheme = darkColorScheme(
////    background = Color(0xFF121212),  // Dark gray background
////    surface = Color(0xFF1E1E1E),     // Dark surface for cards, lists
////    onPrimary = Color(0xFF000000),   // Black text on primary green
////    onSecondary = Color(0xFF000000), // Black text on secondary green
////    onTertiary = Color(0xFF000000),  // Black text on tertiary green
////    onBackground = Color(0xFFFFFFFF),// White text on dark background
////    onSurface = Color(0xFFFFFFFF),   // White text on dark surface
////    primary = Color(0xFF66BB6A),     // Lighter green for primary elements
////    secondary = Color(0xFF81C784),   // Muted green for secondary elements
////    tertiary = Color(0xFFA5D6A7),    // Light green for tertiary elements
////    error = Color(0xFFCF6679),       // Red for error states, slightly muted for dark theme
////)
////val LightColorScheme = lightColorScheme(
////    background = Color(0xFFF1F8E9),  // Soft pastel green
////    surface = Color(0xFFFFFFFF),     // White surface
////    onPrimary = Color(0xFFFFFFFF),   // White text on primary green
////    onSecondary = Color(0xFF000000), // Black text on secondary light green
////    onTertiary = Color(0xFF000000),  // Black text on tertiary light green
////    onBackground = Color(0xFF000000),// Black text on background
////    onSurface = Color(0xFF000000),   // Black text on white surface
////    primary = Color(0xFF2E7D32),     // Dark green
////    secondary = Color(0xFF66BB6A),   // Light green
////    tertiary = Color(0xFFA5D6A7),    // Very light green
////    error = Color(0xFFD32F2F),       // Red for error states
////)
//
//
//// Light Theme Colors
//val LightColorScheme = lightColorScheme(
//    primary = Color(0xFF69A9E5),  // Main primary color
//    onPrimary = Color.White,
//    primaryContainer = Color(0xFFA9D3EC),  // Light version of primary
//    onPrimaryContainer = Color(0xFF5177AC),
//    secondary = Color(0xFF73B5E8),
//    onSecondary = Color.White,
//    background = Color(0xFFFDF6F2),  // Light background
//    onBackground = Color.Black,
//    surface = Color(0xFFD5E6F0),  // Surface color
//    onSurface = Color.Black
//)
//
//// Dark Theme Colors
//val DarkColorScheme = darkColorScheme(
//    primary = Color(0xFF6393DA),  // Darker version of primary
//    onPrimary = Color.Black,
//    primaryContainer = Color(0xFF5177AC),
//    onPrimaryContainer = Color.White,
//    secondary = Color(0xFF5B8AC3),
//    onSecondary = Color.Black,
//    background = Color(0xFF121212),  // Dark mode background
//    onBackground = Color.White,
//    surface = Color(0xFF43598C),
//    onSurface = Color.White
//)
//
//
//@Composable
//fun HelloComposeTheme(
//    darkTheme: Boolean = isSystemInDarkTheme(),
//    content: @Composable() () -> Unit
//) {
//    val colors = if (darkTheme) {
//        DarkColorScheme
//    } else {
//        LightColorScheme
//    }
//
//    MaterialTheme(
//        colorScheme = colors,
//        typography = Typography,
//        shapes = Shapes,
//        content = content
//    )
//}