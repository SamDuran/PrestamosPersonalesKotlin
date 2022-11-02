package edu.ucne.prestamospersonales.data.remote.dto


data class ArticuloDto(
    val articuloId : Int=0,
    val descripcion : String,
    val marca : String,
    val existencia : Double
)
