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

/* ||========== DYNAMIC COLORS ==========|| */
/*   NOTE: Colors 1 LIGHTLY THAN Colors 2   */

@Composable //BG 1
fun bgVariant1() : Color = if (isSystemInDarkTheme()) Color(0xFF061250) else Color.White
@Composable //BG 2
fun bgVariant2() : Color = if (isSystemInDarkTheme()) Color(0xFF030C38) else Color(0xFFADC3FF)

@Composable//Bar 1
fun bgBar1() : Color = if (isSystemInDarkTheme()) Color(0xFF00308B) else Color(0xFF0850B6)
@Composable//Bar 2
fun bgBar2() : Color = if (isSystemInDarkTheme()) Color(0xFF001C59) else Color(0xFF022C68)

@Composable//Card 1
fun bgCard1() : Color = if (isSystemInDarkTheme()) Color(0xFF002F92) else Color(0xFF0063D3)
@Composable //card 2
fun bgCard2() : Color = if (isSystemInDarkTheme()) Color(0xFF002574) else Color(0xFF0069E1)














