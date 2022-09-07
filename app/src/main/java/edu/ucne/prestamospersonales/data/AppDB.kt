package edu.ucne.prestamospersonales.data

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.prestamospersonales.data.dao.DaoOcupacion
import edu.ucne.prestamospersonales.models.Ocupacion

@Database(
    entities =[Ocupacion::class],
    version = 1
)

abstract class AppDB : RoomDatabase() {
    abstract val daoOcupacion : DaoOcupacion
}