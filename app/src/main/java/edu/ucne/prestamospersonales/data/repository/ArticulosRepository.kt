package edu.ucne.prestamospersonales.data.repository

import edu.ucne.prestamospersonales.data.remote.ArticulosApi
import edu.ucne.prestamospersonales.data.remote.dto.ArticuloDto
import javax.inject.Inject

class ArticulosRepository @Inject constructor(
    private val api : ArticulosApi
) {
    suspend fun save(
        articuloId : Int, descripcion : String,
        marca : String, existencia : Double
    ) : ArticuloDto {
        try{
            return api.save(articuloId,descripcion,marca,existencia.toString())
        }catch (e : Exception) {
            throw e
        }
    }

    suspend fun getList() : List<ArticuloDto> {
        try {
            return api.getList()
        }catch (e : Exception) {
            throw e
        }
    }
}