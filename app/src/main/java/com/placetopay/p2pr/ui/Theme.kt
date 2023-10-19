package com.placetopay.p2pr.ui

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Orange600,
    onPrimary = White,
    primaryContainer = Orange600,
    onPrimaryContainer = White,
    secondary = Gray800,
    onSecondary = White,
    secondaryContainer = Gray800,
    onSecondaryContainer = White,
    tertiary = Gray800,
    onTertiary = Gray400,
    tertiaryContainer = Gray50,
    onTertiaryContainer = Gray400,
    background = Gray900,
    onBackground = Gray50,
    error = Red500,
    onError = White,
    surface = Gray900,
    onSurface = White,
)

private val LightColorScheme = lightColorScheme(
    primary = Orange600,
    onPrimary = White,
    primaryContainer = Orange600,
    onPrimaryContainer = White,
    secondary = White,
    onSecondary = Gray900,
    secondaryContainer = White,
    onSecondaryContainer = Gray900,
    tertiary = Gray50,
    onTertiary = Gray900,
    tertiaryContainer = Gray50,
    onTertiaryContainer = Gray400,
    background = Gray50,
    onBackground = Gray900,
    error = Red500,
    onError = White,
    surface = Gray50,
    onSurface = Gray900,
)

@Composable
fun P2prTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
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
        typography = Typography,
        content = content
    )
}