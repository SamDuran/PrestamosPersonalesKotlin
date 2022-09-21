package repository

import data.AppDataBase
import data.entities.Prestamo
import javax.inject.Inject

class PrestamosRepository @Inject constructor(
    private val db : AppDataBase
) {
    suspend fun insert(prestamo : Prestamo) {
        db.prestamoDao.insert(prestamo)
    }

    suspend fun delete(prestamo: Prestamo) {
        db.prestamoDao.delete(prestamo)
    }

    suspend fun find(id: Int) = db.prestamoDao.find(id)

    suspend fun getList() = db.prestamoDao.getList()

    fun findOcupacion(id: Int) = db.ocupacionDao.find(id)

    fun findPersona(id: Int) = db.personaDao.find(id)
    suspend fun getPersonaList() = db.personaDao.getList()
}
