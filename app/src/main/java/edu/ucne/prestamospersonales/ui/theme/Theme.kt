package edu.ucne.prestamospersonales.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val DarkColorPalette = darkColors(

    background = BlueBGDarkMode, //bg
    primary = LightlyBlue,  //Fondo de botones y al momento de seleccionar un tf
    secondary = RegularBlue, // FloatActionButton
    surface = RegularBlueBG, // bg de cards y de top/bottom bars
    onSurface = LightlyGold, //Letras dentro de cards o dentro de bars
    onPrimary = RegularBlue,
    onSecondary = LightlyGold,
)


@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
    primary = RegularBlue,
    primaryVariant = LightlyBlue,
    secondary = LightBlue,
    surface = RegularBlue,
    onSecondary = Color.White
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
