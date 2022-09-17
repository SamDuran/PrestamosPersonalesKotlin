package edu.ucne.prestamospersonales.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

@SuppressLint("ConflictingOnColor")
private val DarkColorPalette = darkColors(

    background = BlueBGDarkMode, //bg
    primary = LightlyGold,  //Fondo de botones y al momento de seleccionar un tf
    secondary = RegularBlue, // FloatActionButton
    surface = RegularBlueBG, // bg de cards y de top/bottom bars
    onSurface = LightlyGold, //Letras dentro de cards o dentro de bars
    onPrimary = RegularBlue,
    onSecondary = LightlyBlue,
)

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
    primary = RegularBlue,
    primaryVariant = LightBlue,
    secondary = RegularBlue,
    surface = RegularBlue,
    onSurface = BlueBGDarkMode
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
