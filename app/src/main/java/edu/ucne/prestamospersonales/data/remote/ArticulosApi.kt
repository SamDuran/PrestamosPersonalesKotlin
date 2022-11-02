package edu.ucne.prestamospersonales.data.remote

import edu.ucne.prestamospersonales.data.remote.dto.ArticuloDto
import retrofit2.Response
import retrofit2.http.*

interface ArticulosApi {


    @GET("api/Articulos")
    suspend fun getList() : List<ArticuloDto>

    @POST("api/Articulos")
    suspend fun save(@Body articulo : ArticuloDto) : Response<ArticuloDto>

    @GET("api/Articulos/{id}")
    suspend fun getArticulo(@Path("id") id: Int): ArticuloDto?

    @PUT("api/Articulos/{id}")
    suspend fun updateArticulo(@Path("id")  id:Int, @Body newArticulo: ArticuloDto): Response<ArticuloDto>

    @DELETE("api/Articulos/{id}")
    suspend fun deleteArticulo(@Path("id")  id:Int): Response<ArticuloDto>
}