package data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="Ocupaciones")
data class Ocupacion(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    @ColumnInfo(name="descripcion") val descripcion: String="",
    val salario: Float=0.0f
){
    override fun toString() : String = descripcion
}
