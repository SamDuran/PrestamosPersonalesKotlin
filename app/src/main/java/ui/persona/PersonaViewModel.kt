package ui.persona

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import data.entities.Persona
import kotlinx.coroutines.launch
import repository.PersonaRepository
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PersonaViewModel @Inject constructor(
    private val repository: PersonaRepository
) : ViewModel() {
    var nombres by mutableStateOf("")
    var telefono by mutableStateOf("")
    var celular by mutableStateOf("")
    var email by mutableStateOf("")
    var direccion by mutableStateOf("")
    var fechaNacimiento by mutableStateOf(Date())
    var ocupacionId by mutableStateOf(0)

    fun save() {
        viewModelScope.launch {
            repository.insert(
                Persona(
                    nombres = nombres,
                    telefono = telefono,
                    celular = celular,
                    email = email,
                    direccion = direccion,
                    fechaNacimiento = fechaNacimiento,
                    ocupacionId = ocupacionId
                )
            )
        }
    }
}