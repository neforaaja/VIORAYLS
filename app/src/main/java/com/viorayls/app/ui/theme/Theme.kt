package com.viorayls.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.viorayls.app.ui.theme.Inter

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFFFFFFF),
    surface = Color(0xFF121212),
    background = Color(0xFF000000),
    surfaceContainerHighest = Color(0xFF1A1A1A),
    onPrimary = Color(0xFF000000),
    onSurface = Color(0xFFFFFFFF),
    onBackground = Color(0xFFFFFFFF),
    error = Color(0xFFFFFFFF)
)

@Composable
fun AppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colorScheme = if (darkTheme) DarkColorScheme else darkColorScheme(
        primary = Color(0xFFFFFFFF),
        surface = Color(0xFF121212),
        background = Color(0xFF000000),
        surfaceContainerHighest = Color(0xFF1A1A1A),
        onPrimary = Color(0xFF000000),
        onSurface = Color(0xFFFFFFFF),
        onBackground = Color(0xFFFFFFFF)
    )
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Inter,
        content = content
    )
}
