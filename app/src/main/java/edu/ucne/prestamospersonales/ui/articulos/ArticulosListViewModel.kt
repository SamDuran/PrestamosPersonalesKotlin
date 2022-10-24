package edu.ucne.prestamospersonales.ui.articulos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.prestamospersonales.data.remote.dto.ArticuloDto
import edu.ucne.prestamospersonales.data.repository.ArticulosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ArticulosListUiState(
    val articulos : List<ArticuloDto> = emptyList()
)

@HiltViewModel
class ArticulosListViewModel @Inject constructor(
    private val repository: ArticulosRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ArticulosListUiState())
    val uiState : StateFlow<ArticulosListUiState> = _uiState.asStateFlow()

    init{
        viewModelScope.launch {
                _uiState.update {
                    it.copy(articulos = repository.getList())
                }

        }
    }
}
