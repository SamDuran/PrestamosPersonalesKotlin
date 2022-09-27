package edu.ucne.prestamospersonales.ui.ocupacion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.prestamospersonales.data.local.entities.Ocupacion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import edu.ucne.prestamospersonales.data.repository.OcupacionRepository
import javax.inject.Inject

data class OcupacionListUiState(
    val ocupaciones : List<Ocupacion> = emptyList()
)

@HiltViewModel
class OcupacionListViewModel @Inject constructor(
    private val repository: OcupacionRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(OcupacionListUiState())
    val uiState : StateFlow<OcupacionListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAll().collect { list ->
                _uiState.update {
                    it.copy(ocupaciones = list)
                }
            }
        }
    }
}
