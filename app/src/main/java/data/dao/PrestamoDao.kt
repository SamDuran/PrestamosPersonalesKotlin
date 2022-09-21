package data.dao

import androidx.room.*
import data.entities.Prestamo
import kotlinx.coroutines.flow.Flow

@Dao
interface PrestamoDao {

    @Insert
    suspend fun insert(prestamo: Prestamo)

    @Update
    suspend fun update(prestamo: Prestamo)

    @Delete
    suspend fun delete(prestamo: Prestamo)

    @Query("SELECT * FROM Prestamos WHERE prestamoId= :id")
    suspend fun find(id: Int): Prestamo

    @Query("SELECT * FROM Prestamos LIMIT 100")
    suspend fun getList(): List<Prestamo>

}