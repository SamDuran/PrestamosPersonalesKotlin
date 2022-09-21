package repository

import data.AppDataBase
import data.entities.Persona
import javax.inject.Inject

class PersonaRepository @Inject constructor(
    private val db: AppDataBase
) {
    suspend fun insert(persona: Persona) {
        db.personaDao.insert(persona)
    }

    suspend fun delete(persona: Persona) {
        db.personaDao.delete(persona)
    }

    fun getPersona(id: Int) = db.personaDao.find(id)

    fun getOcupacion(id: Int) = db.ocupacionDao.find(id)

    fun getList() = db.personaDao.getList()
}