package edu.ucne.prestamospersonales.ui.prestamo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.prestamospersonales.data.local.entities.Prestamo
import edu.ucne.prestamospersonales.data.repository.PrestamosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PrestamosListUiState(
    val prestamos: List<Prestamo> = emptyList(),
)

@HiltViewModel
class PrestamosListViewModel @Inject constructor(
    private val repository: PrestamosRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(PrestamosListUiState())
    val uiState : StateFlow<PrestamosListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getList().collect { lista->
                _uiState.update{
                    it.copy(prestamos = lista)
                }
            }
        }
    }

    fun getPersona(personaId: Int) : String {
        var persona by mutableStateOf("")
        viewModelScope.launch {
            repository.findPersona(personaId).collect{ nombre ->
                persona = nombre
            }
        }
        return persona
    }
    fun getOcupacion(prestamo : Prestamo) : String?{
        var ocupacion1: String? = null
        viewModelScope.launch {
            repository.findOcupacion(prestamo.prestamoId).collect { ocupacion ->
                ocupacion1 = ocupacion
            }
        }
        return ocupacion1
    }
}