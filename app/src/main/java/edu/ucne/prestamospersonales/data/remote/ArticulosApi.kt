package edu.ucne.prestamospersonales.data.remote

import edu.ucne.prestamospersonales.data.remote.dto.ArticuloDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ArticulosApi {
    @POST("/api/Articulo/save")
    suspend fun save(
        @Query("articuloId") articuloId:Int,
        @Query("descripcion") descripcion: String,
        @Query("marca") marca:String,
        @Query("existencia") existencia:String
    ) : ArticuloDto

    @GET("api/Articulos")
    suspend fun getList() : List<ArticuloDto>
}