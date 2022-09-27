package edu.ucne.prestamospersonales.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import edu.ucne.prestamospersonales.util.DateConverter
import java.util.*

@Entity(tableName = "Personas")
data class Persona(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val nombres: String = "",
    val telefono: String = "",
    val celular: String = "",
    val email: String = "",
    val direccion: String = "",
    @TypeConverters(DateConverter::class)
    val fechaNacimiento: Date,
    val ocupacionId : Int,
    val balance: Double
)

