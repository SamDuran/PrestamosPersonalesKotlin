package edu.ucne.prestamospersonales.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.prestamospersonales.data.local.AppDataBase
import edu.ucne.prestamospersonales.util.DateConverter
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context:Context) : AppDataBase {
        return Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            "DBPrestamosPersonales.db"
        ).fallbackToDestructiveMigration().addTypeConverter(DateConverter()).build()

    }
}