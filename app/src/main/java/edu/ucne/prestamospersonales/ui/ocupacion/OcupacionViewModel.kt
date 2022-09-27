package edu.ucne.prestamospersonales.ui.ocupacion

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.prestamospersonales.data.local.entities.Ocupacion
import kotlinx.coroutines.launch
import edu.ucne.prestamospersonales.data.repository.OcupacionRepository
import javax.inject.Inject

data class OcupacionUiState(
    val id: Int? = 0,
    val descripcion: String = "",
    val salario: String = "",
)
@HiltViewModel
class OcupacionViewModel @Inject constructor(
    private val repository: OcupacionRepository,
) : ViewModel() {
    var uiState by mutableStateOf(OcupacionUiState())


    fun save() {
        viewModelScope.launch {
            repository.insert(
                Ocupacion(
                    id = uiState.id!!,
                    descripcion = uiState.descripcion,
                    salario = uiState.salario.toFloat()
                )
            )
        }
    }
    fun setNew() {
        uiState = uiState.copy(id=0, descripcion = "", salario = "")
    }
    fun delete() {
        viewModelScope.launch {
            repository.getOcupacion(uiState.id!!)?.let {
                val ocupacion = it
                repository.delete(ocupacion)
            }
        }
    }
    fun findById(ocupacionId: Int) {
        viewModelScope.launch {
            repository.getOcupacion(ocupacionId)?.let {
                uiState = uiState.copy(
                    id = it.id,
                    descripcion = it.descripcion,
                    salario = it.salario.toString()
                )
            }

        }
    }

    fun setDescripcion(descripcion: String){
        uiState = uiState.copy(descripcion = descripcion)
    }
    fun setSalario(salario: String){
        uiState = uiState.copy(salario = salario)
    }
}

