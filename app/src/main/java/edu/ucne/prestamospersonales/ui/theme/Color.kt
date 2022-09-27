package edu.ucne.prestamospersonales.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val BlueBGDarkMode = Color( 0xFF0C1432)
val RegularBlueBG2 = Color( 0xFF091744)
val RegularBlueBG = Color( 0xFF00061F)
val RegularBlue = Color(0xFF112B8A)
val LightBlue = Color(0xFF2858FF)
val LightlyBlue = Color(0xFFD1FFFD)
val LightlyGold = Color(0xFFFEFFE0)
val ExtremelyLightBlue = Color( 0xFFF9FFFF)
val ExtremelyLightBlue2 = Color( 0xFFF1FFFF)


@Composable
fun bgVariant() : Color = if (isSystemInDarkTheme()) Color(0xFF000A23) else Color.White

@Composable
fun bgVariant1() : Color = if (isSystemInDarkTheme()) Color(0xFF001037) else Color(0xFFEDF2FF)

@Composable
fun bgVariant2() : Color = if (isSystemInDarkTheme()) Color(0xFF002964) else Color(0xFF83C3FF)

@Composable
fun bgVariant3() : Color = if (isSystemInDarkTheme()) Color(0xFF00388B) else Color(0xFF2F9AFF)

@Composable
fun bgVariant4() : Color = if (isSystemInDarkTheme()) Color(0xFF002761) else Color(0xFF003786)

@Composable
fun surfaceVariant() : Color = if (isSystemInDarkTheme()) Color(0xFF002356) else Color(0xFF002ED0)

@Composable
fun surfaceVariant2() : Color = if (isSystemInDarkTheme()) Color(0xFF071F74) else Color(0xFF0025A7)















