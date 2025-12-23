package tech.kaustubhdeshpande.daywise.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

// ============================================
// THEME
// ============================================

@Composable
fun AppTheme(
    useDarkTheme: Boolean?  = null,
    content: @Composable () -> Unit
) {
    val darkTheme = useDarkTheme ?: isSystemInDarkTheme()

    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = AppColors.DarkPrimary,
            onPrimary = AppColors.DarkOnPrimary,
            background = AppColors.DarkBackground,
            onBackground = AppColors.DarkTextPrimary,
            surface = AppColors.DarkSurface,
            onSurface = AppColors.DarkTextPrimary
        )
    } else {
        lightColorScheme(
            primary = AppColors.LightPrimary,
            onPrimary = AppColors.LightOnPrimary,
            background = AppColors.LightBackground,
            onBackground = AppColors.LightTextPrimary,
            surface = AppColors.LightSurface,
            onSurface = AppColors.LightTextPrimary
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
