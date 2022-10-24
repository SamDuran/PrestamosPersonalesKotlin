package edu.ucne.prestamospersonales.ui.persona

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.prestamospersonales.data.local.entities.Persona
import edu.ucne.prestamospersonales.data.repository.PersonaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PersonaListUiState(
    val personas : List<Persona> = emptyList()
)


@HiltViewModel
class PersonaListViewModel @Inject constructor(
    private val repository: PersonaRepository
) : ViewModel () {

    private val _uiState = MutableStateFlow(PersonaListUiState())
    val uiState : StateFlow<PersonaListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getList().collect { list ->
                _uiState.update {
                    it.copy(personas = list)
                }
            }
        }
    }
    fun getOcupacion(id: Int) : String?{
        var descripcion:String? = ""
        viewModelScope.launch {
            repository.getOcupacion(id)?.let { response ->
                descripcion = response
            }
        }
        return descripcion
    }
}