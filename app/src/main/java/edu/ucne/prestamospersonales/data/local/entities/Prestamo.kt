package edu.ucne.prestamospersonales.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import edu.ucne.prestamospersonales.util.DateConverter
import java.util.*

@Entity(tableName="Prestamos")
data class Prestamo(
    @PrimaryKey(autoGenerate = true)
    val prestamoId: Int = 0,
    @TypeConverters(DateConverter::class)
    val fecha: Date = Date(),
    @TypeConverters(DateConverter::class)
    val vence: Date = Date(),
    val personaId : Int = 0,
    val concepto: String = "",
    val monto: Double = 0.0,
    val balance: Double = 0.0
)
