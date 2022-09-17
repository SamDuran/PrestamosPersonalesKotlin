package ui.persona_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import data.entities.Ocupacion
import data.entities.Persona
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import repository.PersonaRepository
import javax.inject.Inject

data class PersonaListUiState(
    val personas : List<Persona> = emptyList()
)
data class PersonaOcupacionUiState(
    val ocupacion : Ocupacion = Ocupacion()
)

@HiltViewModel
class PersonaListViewModel @Inject constructor(
    private val repository: PersonaRepository
) : ViewModel () {
    var ocupacion by mutableStateOf(Ocupacion())
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
    fun getOcupacion(id: Int) {
        viewModelScope.launch {
            repository.getOcupacion(id).collect { response ->
                ocupacion = response
            }
        }
    }
}