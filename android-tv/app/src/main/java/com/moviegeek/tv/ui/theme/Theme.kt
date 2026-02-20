package com.moviegeek.tv.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AppColors = darkColorScheme(
    primary = Color(0xFFF43F5E),
    secondary = Color(0xFF38BDF8),
    background = Color(0xFF0B1020),
    surface = Color(0xFF13203A),
    onPrimary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun MovieGeekTvTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AppColors,
        typography = androidx.compose.material3.Typography(),
        content = content
    )
}
