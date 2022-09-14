package ui.ocupacion

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import data.entities.Ocupacion
import kotlinx.coroutines.launch
import repository.OcupacionRepository
import javax.inject.Inject

@HiltViewModel
class OcupacionViewModel @Inject constructor (
    private val repository : OcupacionRepository
) : ViewModel(){
    var descripcion by mutableStateOf("")
    var salario by mutableStateOf(0f)

    fun save() {
        viewModelScope.launch {
            repository.insert(
                Ocupacion(
                    descripcion = descripcion,
                    salario = salario
                )
            )
        }
    }
}