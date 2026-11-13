package com.example.apppetiveexplorer.menu.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Paleta cálida inspirada en café: tostado, crema, dorado como acento
val CoffeeBrown = Color(0xFF4E342E)
val CoffeeBrownDark = Color(0xFF2E1B16)
val CaramelAccent = Color(0xFFD98324)
val CreamBackground = Color(0xFFFFF8F0)
val CardCream = Color(0xFFFFF1E0)
val TextDark = Color(0xFF3E2723)
val TextMuted = Color(0xFF8D6E63)
val SuccessGreen = Color(0xFF6B8E23)

private val LightColors = lightColorScheme(
    primary = CoffeeBrown,
    onPrimary = Color.White,
    secondary = CaramelAccent,
    onSecondary = Color.White,
    background = CreamBackground,
    onBackground = TextDark,
    surface = CardCream,
    onSurface = TextDark,
    surfaceVariant = CardCream,
)

private val DarkColors = darkColorScheme(
    primary = CaramelAccent,
    onPrimary = Color.Black,
    secondary = CaramelAccent,
    onSecondary = Color.Black,
    background = CoffeeBrownDark,
    onBackground = Color(0xFFF3E5DC),
    surface = Color(0xFF3E2723),
    onSurface = Color(0xFFF3E5DC),
)

@Composable
fun CafeteriaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}
