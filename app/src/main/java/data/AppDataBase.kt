package data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import data.dao.*
import data.entities.*
import util.Converters

@Database(
    entities = [Ocupacion::class, Persona::class, Prestamo::class],
    exportSchema = false,
    version = 2
)

@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {
    abstract val ocupacionDao: OcupacionDao
    abstract val personaDao: PersonaDao
    abstract val prestamoDao: PrestamoDao
}