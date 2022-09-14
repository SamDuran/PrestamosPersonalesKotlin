package edu.ucne.prestamospersonales

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.prestamospersonales.ui.theme.PrestamosPersonalesTheme
import ui.ocupacion.OcupacionScreen
import ui.ocupacion_list.OcupacionListScreen
import ui.persona.PersonaScreen
import ui.persona_list.PersonaListScreen
import util.Screen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PrestamosPersonalesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.PersonaListScreen.ruta
                    ) {
                        composable(Screen.OcupacionListScreen.ruta) {
                            OcupacionListScreen(
                                AddClick = { navController.navigate(Screen.OcupacionScreen.ruta) }
                            )
                        }
                        composable(Screen.PersonaListScreen.ruta) {
                            PersonaListScreen(
                                AddClick = { navController.navigate(Screen.PersonaScreen.ruta) }
                            )
                        }
                        composable(Screen.OcupacionScreen.ruta) {
                            OcupacionScreen({ navController.navigateUp() })
                        }
                        composable(Screen.PersonaScreen.ruta) {
                            PersonaScreen (
                                onNavigateBack= { navController.navigateUp() },
                                AddOcupacionClick = {navController.navigate(Screen.OcupacionScreen.ruta)}
                            )
                        }
                    }
                }
            }
        }
    }
}
