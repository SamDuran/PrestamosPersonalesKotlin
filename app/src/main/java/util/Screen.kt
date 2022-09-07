package util

sealed class Screen(
    val ruta: String
) {
    object OcupacionScreen : Screen("Ocupacion")
    object OcupacionListScreen : Screen("OcupacionList")
}
