package edu.ucne.prestamospersonales.ui.persona

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.prestamospersonales.data.local.entities.Persona
import edu.ucne.prestamospersonales.data.repository.PersonaRepository
import edu.ucne.prestamospersonales.util.DateConverter
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

data class PersonaUiState(
    val id: Int? = 0,
    val nombres: String = "",
    val telefono: String = "",
    val celular: String = "",
    val email: String = "",
    val direccion: String = "",
    val fechaNacimiento: Date = Date(),
    val ocupacionId: Int = 0,
    val balance: Double = 0.0,
)

@HiltViewModel
class PersonaViewModel @Inject constructor(
    private val repository: PersonaRepository,
) : ViewModel() {
    var uiState by mutableStateOf(PersonaUiState())

    var ocupacionSelected by mutableStateOf("Ocupaciones")
    var fechaNacimiento by mutableStateOf("")

    fun save() {
        viewModelScope.launch {
            repository.insert(
                Persona(
                    nombres = uiState.nombres,
                    telefono = uiState.telefono,
                    celular = uiState.celular,
                    email = uiState.email,
                    direccion = uiState.direccion,
                    fechaNacimiento = uiState.fechaNacimiento,
                    ocupacionId = uiState.ocupacionId,
                    balance = uiState.balance
                )
            )
        }
    }

    fun setNew() {
        uiState = uiState.copy(
            id = 0,
            nombres = "",
            telefono = "",
            celular = "",
            email = "",
            direccion = "",
            fechaNacimiento = Date(),
            ocupacionId = 0,
            balance = 0.0
        )
        ocupacionSelected ="Ocupaciones"
        fechaNacimiento = ""
    }

    fun delete() {
        viewModelScope.launch {
            repository.find(uiState.id!!)?.let {
                repository.delete(it)
            }
        }
    }

    fun setId(id: Int) {
        uiState = uiState.copy(id = id)
    }

    fun setNombres(nombres: String) {
        uiState = uiState.copy(nombres = nombres)
    }

    fun setTelefono(telefono: String) {
        uiState = uiState.copy(telefono = telefono)
    }

    fun setCelular(Celular: String) {
        uiState = uiState.copy(celular = Celular)
    }

    fun setEmail(email: String) {
        uiState = uiState.copy(email = email)
    }

    fun setDireccion(direccion: String) {
        uiState = uiState.copy(direccion = direccion)
    }

    fun setFecha(fecha: Date) {
        uiState = uiState.copy(fechaNacimiento = fecha)
        fechaNacimiento = DateConverter().toString(fecha)
    }

    fun setOcupacion(id: Int) {
        uiState = uiState.copy(ocupacionId = id)
    }

    fun setBalance(balance: Double) {
        uiState = uiState.copy(balance = balance)
    }

    private fun loadOcupacion(ocupacionId: Int) {
        viewModelScope.launch {
            repository.getOcupacion(ocupacionId)?.let {
                ocupacionSelected = it.descripcion
            }
        }
    }

    fun find(personaId: Int) {
        viewModelScope.launch {
            repository.find(personaId)?.let {
                uiState = uiState.copy(
                    id = it.id,
                    nombres = it.nombres,
                    telefono = it.telefono,
                    celular = it.celular,
                    email = it.email,
                    direccion = it.direccion,
                    ocupacionId = it.ocupacionId,
                    fechaNacimiento = it.fechaNacimiento,
                    balance = it.balance
                )
                loadOcupacion(ocupacionId = uiState.ocupacionId)
                fechaNacimiento = DateConverter().toString(uiState.fechaNacimiento)
            }
        }
    }
}