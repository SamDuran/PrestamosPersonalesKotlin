package edu.ucne.prestamospersonales

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.prestamospersonales.ui.NavigationManager
import edu.ucne.prestamospersonales.ui.SplashScreen
import edu.ucne.prestamospersonales.ui.theme.PrestamosPersonalesTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PrestamosPersonalesTheme {

                val navController = rememberNavController()
                //Can find the file on UI Package
                NavigationManager(navController = navController)
            }
        }
    }
}
