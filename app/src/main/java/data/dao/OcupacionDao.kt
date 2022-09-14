package data.dao

import androidx.room.*
import data.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface OcupacionDao {
    @Insert
    suspend fun insert(ocupacion: Ocupacion)

    @Update
    suspend fun update(ocupacion: Ocupacion)

    @Delete
    suspend fun delete(ocupacion: Ocupacion)

    @Query("SELECT * FROM Ocupaciones Where id = :id")
    fun find(id:Int): Flow<Ocupacion>



    @Query("SELECT * FROM Ocupaciones")
    fun getList(): Flow<List<Ocupacion>>
}