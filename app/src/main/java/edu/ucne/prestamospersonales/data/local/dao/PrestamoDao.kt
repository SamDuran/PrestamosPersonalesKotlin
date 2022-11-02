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

    @Query("SELECT nombres FROM Personas as Pe Inner Join Prestamos as P on Pe.id = p.personaId WHERE P.prestamoId = :id LIMIT 1")
    suspend fun findPersona(id: Int): String?

    @Query("SELECT descripcion FROM Ocupaciones as O " +
            "inner join Personas as Pe on O.id = Pe.ocupacionId " +
            "inner join Prestamos as Pr on pr.personaId = Pe.id " +
            "where Pr.prestamoId = :id"
    )
    fun findOcupacion(id: Int): Flow<String>

    @Query("SELECT * FROM Prestamos LIMIT 100")
    fun getList(): Flow<List<Prestamo>>


}