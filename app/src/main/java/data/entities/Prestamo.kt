package data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName="Prestamos")
data class Prestamo(
    @PrimaryKey(autoGenerate = true)
    val prestamoId: Int? = 0,
    val fecha: Date = Date(),
    val vence: Date = Date(),
    val personaId : Int = 0,
    val concepto: String = "",
    val monto: Double = 0.0,
    val balance: Double = 0.0
)
