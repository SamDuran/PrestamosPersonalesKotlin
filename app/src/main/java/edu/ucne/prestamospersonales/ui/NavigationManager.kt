package edu.ucne.prestamospersonales.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import edu.ucne.prestamospersonales.ui.articulos.ArticulosListScreen
import edu.ucne.prestamospersonales.ui.ocupacion.OcupacionScreen
import edu.ucne.prestamospersonales.ui.ocupacion.OcupacionListScreen
import edu.ucne.prestamospersonales.ui.persona.PersonaScreen
import edu.ucne.prestamospersonales.ui.persona.PersonaListScreen
import edu.ucne.prestamospersonales.ui.prestamo.PrestamoScreen
import edu.ucne.prestamospersonales.ui.prestamo.PrestamosListScreen
import edu.ucne.prestamospersonales.util.Screen
import androidx.compose.animation.Animatable

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationManager(
    navController: NavHostController,
) {
    val alphaAnimation = remember { androidx.compose.animation.core.Animatable(0f) }
    LaunchedEffect(Unit) {
        alphaAnimation.animateTo(1f)
    }

    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.ruta
    ) {
        //Home Screen
        composable(Screen.HomeScreen.ruta) {

            HomeScreen(navController)
        }
        //Ocupacion Screen
        composable( route = Screen.OcupacionScreen.ruta + "/{id}", arguments = listOf(navArgument("id") { type = NavType.IntType })) { navEntry ->
            val ocupacionId = navEntry.arguments?.getInt("id") ?:0
            OcupacionScreen(ocupacionId ,{ navController.navigateUp() })
        }
        //persona Screen
        composable(Screen.PersonaScreen.ruta+"/{personaId}", arguments = listOf(navArgument("personaId") { type = NavType.IntType })) {
            val personaId = it.arguments?.getInt("personaId")?:0
            PersonaScreen(
                personaId =  personaId,
                onNavigateBack = { navController.navigateUp() },
                AddOcupacionClick = { navController.navigate(Screen.OcupacionScreen.ruta+"/0") }
            )
        }
        //Prestamo Screen
        composable(Screen.PrestamoScreen.ruta+"/{id}", arguments = listOf(navArgument("id") { type = NavType.IntType })) {
            val prestamoId = it.arguments?.getInt("id")?:0
            PrestamoScreen(
                prestamoId = prestamoId,
                onNavigateBack = { navController.navigateUp() },
                addPersonaClick = { navController.navigate(Screen.PersonaScreen.ruta+"/0") }
            )
        }
        //Ocupacion List Screen
        composable(Screen.OcupacionListScreen.ruta) {
            OcupacionListScreen(
                onBackClick = { navController.navigateUp() },
                AddClick = { navController.navigate(Screen.OcupacionScreen.ruta+"/0") }
            ) { id ->
                navController.navigate(Screen.OcupacionScreen.ruta + "/$id")
            }
        }
        //Persona List Screen
        composable(Screen.PersonaListScreen.ruta) {

            PersonaListScreen(
                onNavigationBack = { navController.navigateUp() },
                AddClick = { navController.navigate(Screen.PersonaScreen.ruta+"/0") }
            ){
                navController.navigate(Screen.PersonaScreen.ruta + "/$it")
            }
        }
        //Prestamo List Screen
        composable(Screen.PrestamosListScreen.ruta) {

            PrestamosListScreen(
                onNavigateBack = { navController.navigateUp() },
                addPrestamoClick = { navController.navigate(Screen.PrestamoScreen.ruta+"/0") }
            ){
                navController.navigate(Screen.PrestamoScreen.ruta + "/$it")
            }
        }
        composable(Screen.ArticulosListScreen.ruta) {
            ArticulosListScreen(
                onBackClick ={navController.navigateUp()}
            )
        }
    }
}