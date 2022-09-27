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

    fun getList() = db.prestamoDao.getList()

    //foreign Functions
    suspend fun findOcupacion(id: Int) = db.ocupacionDao.find(id)

    suspend fun findPersona(id: Int) = db.personaDao.find(id)

    suspend fun updatePersona(persona: Persona) = db.personaDao.insert(persona)

    fun getPersonaList() = db.personaDao.getList()
}
