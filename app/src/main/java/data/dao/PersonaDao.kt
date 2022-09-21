package data.dao

import androidx.room.*
import data.entities.Persona
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonaDao {
    @Insert
    suspend fun insert(persona: Persona)

    @Update
    suspend fun update(persona: Persona)

    @Delete
    suspend fun delete(persona: Persona)

    @Query("SELECT * FROM Personas WHERE id = :id")
    fun find(id:Int): Flow<Persona>

    @Query("SELECT * FROM Personas")
    fun getList(): Flow<List<Persona>>
}