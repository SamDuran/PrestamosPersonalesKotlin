package edu.ucne.prestamospersonales.data.repository

import edu.ucne.prestamospersonales.data.local.AppDataBase
import edu.ucne.prestamospersonales.data.local.entities.Ocupacion
import javax.inject.Inject

class OcupacionRepository @Inject constructor(
    private val db : AppDataBase
){
    suspend fun insert(ocupacion : Ocupacion) = db.ocupacionDao.insert(ocupacion)

    suspend fun delete(ocupacion: Ocupacion) = db.ocupacionDao.delete(ocupacion)

    suspend fun getOcupacion(id : Int) = db.ocupacionDao.find(id)

    fun getAll() = db.ocupacionDao.getList()
}