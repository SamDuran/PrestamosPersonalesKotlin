package repository

import data.AppDataBase
import data.entities.Ocupacion
import javax.inject.Inject

class OcupacionRepository @Inject constructor(
    private val db : AppDataBase
){
    suspend fun insert(ocupacion : Ocupacion) {
        db.ocupacionDao.insert(ocupacion)
    }

    suspend fun update(ocupacion : Ocupacion) {
        db.ocupacionDao.update(ocupacion)
    }

    suspend fun delete(ocupacion: Ocupacion) {
        db.ocupacionDao.delete(ocupacion)
    }

    fun getOcupacion(id : Int) = db.ocupacionDao.find(id)

    fun getAll() = db.ocupacionDao.getList()
}