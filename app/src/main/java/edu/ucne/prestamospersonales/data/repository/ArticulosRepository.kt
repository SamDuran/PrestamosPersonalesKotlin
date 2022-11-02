package edu.ucne.prestamospersonales.data.repository

import edu.ucne.prestamospersonales.data.remote.ArticulosApi
import edu.ucne.prestamospersonales.data.remote.dto.ArticuloDto
import retrofit2.Response
import javax.inject.Inject

class ArticulosRepository @Inject constructor(
    private val api : ArticulosApi
) {
    //GET (todos)
    suspend fun getArticulos() : List<ArticuloDto> {
        try {
            return api.getList()
        }catch (e : Exception) {
            throw e
        }
    }
    //POST
    suspend fun postArticulo(articuloDto: ArticuloDto): Response<ArticuloDto> {
        try {
            return api.save(articuloDto)
        }catch(e : Exception){
            throw e
        }
    }
    //GET (uno)
    suspend fun getArticulo(id: Int): ArticuloDto? {
        try {
            return api.getArticulo(id)
        }catch (e:Exception){
            throw e
        }
    }
    //PUT
    suspend fun putArticulo(id:Int, newArticulo:ArticuloDto): Response<ArticuloDto> {
        try {
            return api.updateArticulo(id,newArticulo)
        }catch (e :Exception) {
            throw e
        }
    }
    //DELETE
    suspend fun deleteArticulo(id: Int): Response<ArticuloDto> {
        try {
            return api.deleteArticulo(id)
        }catch (e :Exception) {
            throw e
        }
    }
}