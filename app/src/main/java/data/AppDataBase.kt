package data

import androidx.room.Database
import androidx.room.RoomDatabase
import data.dao.OcupacionDao
import data.entities.Ocupacion

@Database(
    entities = [Ocupacion::class],
    version = 1
)
abstract class AppDataBase : RoomDatabase() {
    abstract val ocupacionDao: OcupacionDao
}
//https://www.youtube.com/watch?v=lYBb4QedYH8  min 5:45