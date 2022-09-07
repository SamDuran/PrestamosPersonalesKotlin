package edu.ucne.prestamospersonales.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

@SuppressLint("ConflictingOnColor")
private val DarkColorPalette = darkColors(
    primary = LightBlue,
    primaryVariant = RegularBlue,
    secondary = Ocean,
    background = DarkBlue,
    surface = RegularBlue,
    onSurface = LightBlue,
    onPrimary = LightGold
)

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
    primary = RegularBlue,
    primaryVariant = DarkBlue,
    secondary = Ocean,
    background = LightBlue,
    surface = MediumBlue,
    onSurface = DarkBlue,
    onPrimary = LightGold


    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun PrestamosPersonalesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}