package ui.persona_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import data.entities.Ocupacion
import data.entities.Persona
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import repository.PersonaRepository
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

}