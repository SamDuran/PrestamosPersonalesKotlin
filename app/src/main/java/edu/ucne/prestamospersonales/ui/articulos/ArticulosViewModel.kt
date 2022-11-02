package edu.ucne.prestamospersonales.ui.articulos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.prestamospersonales.data.remote.dto.ArticuloDto
import edu.ucne.prestamospersonales.data.repository.ArticulosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ArticulosUiState(
    val id: Int = 0,

    val descripcion: String = "",
    val descripcionError: String? = null,

    val marca: String = "",
    val marcaError: String? = null,

    val existencia: String = "",
    val existenciaError: String? = null
)

@HiltViewModel
class ArticulosViewModel @Inject constructor(
    private val repository: ArticulosRepository
) : ViewModel() {
    var uiState = MutableStateFlow(ArticulosUiState())
        private set

    fun findById(id: Int) {
        if (id > 0) {
            viewModelScope.launch {
                val articulo = repository.getArticulo(id)

                uiState.value = uiState.value.copy(
                    id = articulo?.articuloId ?: 0,
                    descripcion = articulo?.descripcion ?: "",
                    marca = articulo?.marca ?: "",
                    existencia = articulo?.existencia.toString()
                )
            }
        }
    }

    fun save(): String {
        if (canSave()) {
            viewModelScope.launch {
                if (uiState.value.id>0) {
                    repository.putArticulo(
                        uiState.value.id,
                        ArticuloDto(
                            articuloId = uiState.value.id,
                            descripcion = uiState.value.descripcion,
                            marca = uiState.value.marca,
                            existencia = uiState.value.existencia.toDouble()
                        )
                    )
                } else {
                    repository.postArticulo(
                        ArticuloDto(
                            descripcion = uiState.value.descripcion,
                            marca = uiState.value.marca,
                            existencia = uiState.value.existencia.toDouble()
                        )
                    )
                }
            }
            return "Guardado correctamente"
        }
        return "No se pudo guardar"
    }

    fun canSave(): Boolean = uiState.value.existenciaError == null
            && uiState.value.descripcionError == null
            && uiState.value.marcaError == null

    fun setDescripcion(descripcion: String) {
        uiState.value = uiState.value.copy(descripcion = descripcion)

        if (uiState.value.descripcion.length < 3) {
            uiState.value =
                uiState.value.copy(descripcionError = "* Ingrese una descripción válida(Al menos 3 caractéres) *")
        } else {
            uiState.value = uiState.value.copy(descripcionError = null)
        }
    }

    fun setMarca(marca: String) {
        uiState.value = uiState.value.copy(marca = marca)

        if (uiState.value.marca.isEmpty()) {
            uiState.value = uiState.value.copy(marcaError = "* Ingrese una marca válida *")
        } else {
            uiState.value = uiState.value.copy(marcaError = null)
        }
    }

    fun setExistencia(existencia: String) {
        uiState.value = uiState.value.copy(existencia = existencia)

        if (existencia.toDouble() < 0) {
            uiState.value =
                uiState.value.copy(existenciaError = "* Ingrese una existencia válida *")
        } else {
            uiState.value = uiState.value.copy(existenciaError = null)
        }
    }

    fun cleanFields() {
        uiState.value = uiState.value.copy(
            id= 0,
            descripcion = "", descripcionError = null,
            marca = "", marcaError = null,
            existencia = "", existenciaError = null
        )
    }

    fun delete() {
        viewModelScope.launch {
            repository.deleteArticulo(uiState.value.id)
        }
    }
}
