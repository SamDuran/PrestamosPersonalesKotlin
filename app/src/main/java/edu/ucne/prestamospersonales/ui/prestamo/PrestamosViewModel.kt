package edu.ucne.prestamospersonales.ui.prestamo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.prestamospersonales.data.local.entities.Persona
import edu.ucne.prestamospersonales.data.local.entities.Prestamo
import edu.ucne.prestamospersonales.data.repository.PrestamosRepository
import edu.ucne.prestamospersonales.util.DateConverter
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

data class PrestamosUiState(
    val fecha: Date = Date(),
    val vence: Date = Date(),
    val prestamoId: Int = 0,
    val personaId: Int=0,
    val concepto: String="",
    val monto: String="",
    val balance: String="",
    val personas: List<Persona> = emptyList()
)

@Suppress("DEPRECATION")
@HiltViewModel
class PrestamosViewModel @Inject constructor(
    private val repository: PrestamosRepository,
) : ViewModel() {
    var uiState by mutableStateOf(PrestamosUiState())

    var personaSelected by mutableStateOf("Persona")
    var fechaVencimiento by mutableStateOf("")
    var fechaPrestamo by mutableStateOf("")

    init{
        val year = Calendar.getInstance().get(Calendar.YEAR)
        val month = Calendar.getInstance().get(Calendar.MONTH)
        val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

        fechaPrestamo = "$day/${month + 1}/$year"
        viewModelScope.launch {
            repository.getPersonaList().let{ lista ->
                ( lista.collect{
                    uiState = uiState.copy(personas = it)
                })
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun save(year:Int,month:Int,day:Int) {
        viewModelScope.launch {
            repository.insert(
                Prestamo(
                    fecha = Date(year, month, day),
                    vence = uiState.vence,
                    prestamoId = uiState.prestamoId,
                    concepto = uiState.concepto,
                    personaId = uiState.personaId,
                    monto = uiState.monto.toDoubleOrNull()?:0.0,
                    balance = uiState.balance.toDoubleOrNull()?:0.0
                )
            )
            //actualizar persona
            repository.getPersona(uiState.personaId)?.let {persona->
                    var newBalance = 0.0
                    repository.find(uiState.prestamoId)?.let{prestamo->
                        newBalance = persona.balance-prestamo.balance+uiState.balance.toDouble()
                    }
                repository.updatePersona(
                    persona.copy(balance = newBalance)
                )
            }
        }
    }
    fun findById(prestamoId: Int) {
        viewModelScope.launch {
            repository.find(prestamoId)?.let {
                uiState = uiState.copy(
                    vence = it.vence,
                    fecha = it.fecha,
                    prestamoId = it.prestamoId,
                    personaId = it.personaId,
                    concepto = it.concepto,
                    monto = it.monto.toString(),
                    balance = it.balance.toString()
                )
                fechaPrestamo = DateConverter().toString(it.fecha)
                fechaVencimiento= DateConverter().toString(it.vence)
                findPersona(it.personaId)
            }
        }
    }

    private fun findPersona(personaId: Int)  {
        viewModelScope.launch {
            repository.findPersona(personaId).collect {
                personaSelected = it
            }
        }
    }

    fun setPersona(id: Int) {
        uiState =uiState.copy(personaId = id)
    }
    fun setFecha(fecha: Date) {
        uiState = uiState.copy(vence = fecha)
    }
    fun setConcepto(concepto: String) {
        uiState = uiState.copy(concepto = concepto)
    }
    fun setBalance(balance: String) {
        uiState = uiState.copy(balance = balance)
    }
    fun setMonto(monto: String) {
        uiState = uiState.copy(monto = monto)
    }

    fun delete() {
        viewModelScope.launch {
            repository.find(uiState.prestamoId)?.let{
                repository.delete(it)
            }
        }
    }

    fun setNew() {
        uiState = uiState.copy(
            prestamoId = 0,
            fecha = Date(),
            vence = Date(),
            personaId = 0,
            concepto = "",
            balance = "",
            monto = ""
        )
        personaSelected = "Persona"
        fechaVencimiento = ""
    }
}