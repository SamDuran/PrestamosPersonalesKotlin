package edu.ucne.prestamospersonales.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Ocupacion (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var descripcion : String = "",
    var salario : Float = 0.0f
)
