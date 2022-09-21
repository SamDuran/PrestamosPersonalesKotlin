package ui.prestamo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import data.entities.Persona
import data.entities.Prestamo
import kotlinx.coroutines.launch
import repository.PrestamosRepository
import java.time.Instant
import java.util.*
import javax.inject.Inject

data class PrestamosUiState(
    val fecha: Date = Date(),
    val vence: Date = Date(),
    val prestamoId: Int? = null,
    val personaId: Int=0,
    val concepto: String="",
    val monto: String="",
    val balance: String="",
    val personas: List<Persona> = emptyList()
)

@HiltViewModel
class PrestamosViewModel @Inject constructor(
    private val repository: PrestamosRepository,
) : ViewModel() {
    var uiState by mutableStateOf(PrestamosUiState())

    init{
        viewModelScope.launch {
            repository.getPersonaList().let{ lista ->
                ( lista.collect{
                    uiState = uiState.copy(personas = it)
                })
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun save() {
        val fecha=Date.from(Instant.now())
        viewModelScope.launch {
            repository.insert(
                Prestamo(
                    fecha = fecha,
                    prestamoId = uiState.prestamoId,
                    concepto = uiState.concepto,
                    personaId = uiState.personaId,
                    monto = uiState.monto.toDoubleOrNull()?:0.0
                )
            )
        }
    }

    fun findById(prestamoId: Int) {
        viewModelScope.launch {
            repository.find(prestamoId).let {
                uiState = uiState.copy(
                    vence = it.vence,
                    prestamoId = it.prestamoId,
                    concepto = it.concepto,
                    monto = it.monto.toString()
                )
            }
        }
    }
    fun setPersona(id: Int) = uiState.copy(personaId = id)
    fun setFecha(fecha: Date) = uiState.copy(vence = fecha)
}