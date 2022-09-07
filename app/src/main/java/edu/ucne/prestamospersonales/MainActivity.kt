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
import edu.ucne.prestamospersonales.ui.theme.PrestamosPersonalesTheme
import ui.ocupacion.OcupacionScreen
import ui.ocupacion_list.OcupacionListScreen
import dagger.hilt.android.AndroidEntryPoint
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
                        startDestination = Screen.OcupacionListScreen.ruta
                    ) {
                        composable(Screen.OcupacionListScreen.ruta) {
                            OcupacionListScreen(

                                onClick = { navController.navigate(Screen.OcupacionScreen.ruta) }
                            )
                        }
                        composable(Screen.OcupacionScreen.ruta) {
                            OcupacionScreen({ navController.navigateUp() })
                        }
                    }
                }
            }
        }
    }
}
