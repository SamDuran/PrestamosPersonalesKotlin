package edu.ucne.prestamospersonales.data.dao

import androidx.room.*
import edu.ucne.prestamospersonales.models.Ocupacion
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoOcupacion {
    @Insert
    fun insertOcupacion(ocupacion : Ocupacion)

    @Update
    suspend fun updateOcupacion(ocupacion : Ocupacion)

    @Delete
    suspend fun deleteOcupacion(ocupacion : Ocupacion)

    @Query("SELECT * FROM Ocupacion WHERE id = :id")
    fun getOcupacion(id: Int): Flow<Ocupacion>

    @Query("SELECT * FROM Ocupacion")
    fun getAll(): Flow<List<Ocupacion>>
}