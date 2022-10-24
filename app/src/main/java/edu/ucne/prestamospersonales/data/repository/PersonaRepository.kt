package edu.ucne.prestamospersonales.data.repository

import edu.ucne.prestamospersonales.data.local.AppDataBase
import edu.ucne.prestamospersonales.data.local.entities.Persona
import javax.inject.Inject

class PersonaRepository @Inject constructor(
    private val db: AppDataBase
) {
    suspend fun insert(persona: Persona) = db.personaDao.insert(persona)

    suspend fun delete(persona: Persona) = db.personaDao.delete(persona)

    suspend fun find(id: Int) = db.personaDao.find(id)

    suspend fun getOcupacion(Id:Int) = db.personaDao.getOcupacion(Id)

    fun getList() = db.personaDao.getList()

}