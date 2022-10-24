package edu.ucne.prestamospersonales.data.local.dao

import androidx.room.*
import edu.ucne.prestamospersonales.data.local.entities.Persona
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(persona: Persona)

    @Delete
    suspend fun delete(persona: Persona)

    @Query("SELECT * FROM Personas WHERE id = :id LIMIT 1")
    suspend fun find(id:Int): Persona?

    @Query("SELECT Descripcion FROM Ocupaciones as O" +
            " Inner Join Personas as P on O.id = P.ocupacionId " +
            "where p.id = :id")
    suspend fun getOcupacion(id:Int): String?

    @Query("SELECT * FROM Personas LIMIT 100")
    fun getList(): Flow<List<Persona>>
}