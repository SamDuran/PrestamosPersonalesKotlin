package edu.ucne.prestamospersonales.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import edu.ucne.prestamospersonales.data.local.dao.OcupacionDao
import edu.ucne.prestamospersonales.data.local.dao.PersonaDao
import edu.ucne.prestamospersonales.data.local.dao.PrestamoDao
import edu.ucne.prestamospersonales.data.local.entities.Ocupacion
import edu.ucne.prestamospersonales.data.local.entities.Persona
import edu.ucne.prestamospersonales.data.local.entities.Prestamo
import edu.ucne.prestamospersonales.util.DateConverter

@Database(
    entities = [Ocupacion::class, Persona::class, Prestamo::class],
    exportSchema = false,
    version = 1
)

@TypeConverters(DateConverter::class)
abstract class AppDataBase : RoomDatabase() {
    abstract val ocupacionDao: OcupacionDao
    abstract val personaDao: PersonaDao
    abstract val prestamoDao: PrestamoDao
}