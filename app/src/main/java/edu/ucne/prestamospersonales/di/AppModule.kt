package edu.ucne.prestamospersonales.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.prestamospersonales.data.local.AppDataBase
import edu.ucne.prestamospersonales.data.remote.ArticulosApi
import edu.ucne.prestamospersonales.util.DateConverter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
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
        )
            .fallbackToDestructiveMigration()
            .addTypeConverter(DateConverter())
            .build()
    }

    @Singleton
    @Provides
    fun provideMoshi() : Moshi {
        return Moshi
            .Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun providesArticulosApi(moshi: Moshi): ArticulosApi {
        return Retrofit.Builder()
            .baseUrl("http://samarticulosapi.somee.com/")
            //.baseUrl("https://gestioninventario.azurewebsites.net/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ArticulosApi::class.java)
    }
}