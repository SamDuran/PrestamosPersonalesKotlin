package edu.ucne.prestamospersonales.util

sealed class Screen(
    val ruta: String
) {
    object OcupacionScreen : Screen("Ocupacion")
    object OcupacionListScreen : Screen("OcupacionList")
    object PersonaScreen : Screen("Persona")
    object PersonaListScreen : Screen("PersonaList")
    object PrestamoScreen : Screen("Prestamo")
    object PrestamosListScreen : Screen("PrestamoList")
    object HomeScreen : Screen("Home")
    object SplashScreen : Screen("SplashScreen")
}
