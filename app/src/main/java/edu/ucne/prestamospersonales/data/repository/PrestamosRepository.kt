package edu.ucne.prestamospersonales.data.repository

import edu.ucne.prestamospersonales.data.local.AppDataBase
import edu.ucne.prestamospersonales.data.local.entities.Persona
import edu.ucne.prestamospersonales.data.local.entities.Prestamo
import javax.inject.Inject

class PrestamosRepository @Inject constructor(
    private val db : AppDataBase
) {
    suspend fun insert(prestamo : Prestamo) = db.prestamoDao.insert(prestamo)

    suspend fun delete(prestamo: Prestamo) = db.prestamoDao.delete(prestamo)

    suspend fun find(id: Int) = db.prestamoDao.find(id)

    suspend fun findPersona(id: Int) = db.prestamoDao.findPersona(id)
    fun getList() = db.prestamoDao.getList()

    suspend fun findOcupacion(id: Int) = db.prestamoDao.findOcupacion(id)

    //foreign Functions
    suspend fun updatePersona(persona: Persona) = db.personaDao.insert(persona)
    suspend fun getPersona(id:Int) = db.personaDao.find(id)

    fun getPersonaList() = db.personaDao.getList()
}
