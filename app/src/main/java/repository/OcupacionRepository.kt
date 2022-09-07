package repository

import data.AppDataBase
import data.entities.Ocupacion
import javax.inject.Inject

class OcupacionRepository @Inject constructor(
    private val db : AppDataBase
){
    suspend fun insertOcupacion(ocupacion : Ocupacion) {
        db.ocupacionDao.insertOcupacion(ocupacion)
    }

    suspend fun updateOcupacion(ocupacion : Ocupacion) {
        db.ocupacionDao.updateOcupacion(ocupacion)
    }

    suspend fun deleteOcupacion(ocupacion: Ocupacion) {
        db.ocupacionDao.deleteOcupacion(ocupacion)
    }

    fun getOcupacion(id : Int) = db.ocupacionDao.getOcupacion(id)

    fun getAll() = db.ocupacionDao.getAll()
}