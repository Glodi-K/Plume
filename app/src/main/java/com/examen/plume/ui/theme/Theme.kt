package com.examen.plume.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import com.examen.plume.ui.viewmodel.ThemeMode

private val DarkColorScheme = darkColorScheme(
    primary = PurplePrimary,
    secondary = PurpleSecondary,
    background = BackgroundDark,
    surface = SurfaceDark,
    onPrimary = OnPrimaryDark,
    onBackground = OnBackgroundDark,
    onSurface = OnSurfaceDark
)

private val LightColorScheme = lightColorScheme(
    primary = PurplePrimary,
    secondary = PurpleSecondary,
    background = BackgroundLight,
    surface = SurfaceLight,
    onPrimary = OnPrimaryLight,
    onBackground = OnBackgroundLight,
    onSurface = OnSurfaceLight
)

@Composable
fun PlumeTheme(
    themeMode: ThemeMode,
    content: @Composable () -> Unit
) {
    val darkTheme = when (themeMode) {
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
        ThemeMode.DARK -> true
        ThemeMode.LIGHT -> false
    }

    val targetColors = if (darkTheme) DarkColorScheme else LightColorScheme

    // Animation fluide des couleurs
    val animatedColorScheme = targetColors.copy(
        primary = animateColorAsState(targetColors.primary, tween(400)).value,
        secondary = animateColorAsState(targetColors.secondary, tween(400)).value,
        background = animateColorAsState(targetColors.background, tween(400)).value,
        surface = animateColorAsState(targetColors.surface, tween(400)).value,
        onPrimary = animateColorAsState(targetColors.onPrimary, tween(400)).value,
        onBackground = animateColorAsState(targetColors.onBackground, tween(400)).value,
        onSurface = animateColorAsState(targetColors.onSurface, tween(400)).value
    )

    MaterialTheme(
        colorScheme = animatedColorScheme,
        typography = Typography,
        content = content
    )
}