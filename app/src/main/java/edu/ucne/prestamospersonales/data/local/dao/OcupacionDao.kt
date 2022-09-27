package edu.ucne.prestamospersonales.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import edu.ucne.prestamospersonales.data.local.entities.Ocupacion

@Dao
interface OcupacionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ocupacion: Ocupacion)

    @Delete
    suspend fun delete(ocupacion: Ocupacion)

    @Query("SELECT * FROM Ocupaciones Where id = :id")
    suspend fun find(id:Int): Ocupacion?

    @Query("SELECT * FROM Ocupaciones")
    fun getList(): Flow<List<Ocupacion>>
}