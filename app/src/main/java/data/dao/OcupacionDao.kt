package data.dao

import androidx.room.*
import data.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface OcupacionDao {
    @Insert
    suspend fun insertOcupacion(ocupacion: Ocupacion)

    @Update
    suspend fun updateOcupacion(ocupacion: Ocupacion)

    @Delete
    suspend fun deleteOcupacion(ocupacion: Ocupacion)

    @Query("SELECT * FROM Ocupaciones Where id = :id")
    fun getOcupacion(id:Int): Flow<Ocupacion>

    @Query("SELECT * FROM Ocupaciones")
    fun getAll(): Flow<List<Ocupacion>>
}