package edu.ucne.prestamospersonales.data.local.dao

import androidx.room.*
import edu.ucne.prestamospersonales.data.local.entities.Prestamo
import kotlinx.coroutines.flow.Flow

@Dao
interface PrestamoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(prestamo: Prestamo)

    @Delete
    suspend fun delete(prestamo: Prestamo)

    @Query("SELECT * FROM Prestamos WHERE prestamoId = :id LIMIT 1")
    suspend fun find(id: Int): Prestamo?

    @Query("SELECT * FROM Prestamos LIMIT 100")
    fun getList(): Flow<List<Prestamo>>
}