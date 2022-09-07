package ui

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

    fun Save() {
        viewModelScope.launch {
            repository.insertOcupacion(
                Ocupacion(
                    descripcion = descripcion,
                    salario = salario
                )
            )
        }
    }
}