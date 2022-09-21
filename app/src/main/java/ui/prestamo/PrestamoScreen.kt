package ui.prestamo

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import util.OutlineTextFieldWithErrorView
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PrestamoScreen(
    prestamoId: Int = 0,
    viewModel: PrestamosViewModel = hiltViewModel(),
    addPersonaClick: () -> Unit,
    onNavigateBack: () -> Unit,
) {
//    val id= remember(prestamoId){
//        viewModel.findById(prestamoId)
//        0
//    }

    val context = LocalContext.current
    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)
    val fecha = "$day - ${month + 1} - $year"

    var fechaVencimiento by remember { mutableStateOf("") }
    @Suppress("DEPRECATION") val datePicker = DatePickerDialog(
        context, { _, year1, month1, day1 ->
            val month2: Int = month1 + 1
            //onValueChange de fecha nacimiento
            fechaVencimiento = "$day1 - $month2 - $year1"
            viewModel.setFecha(Date(year1, month2, day1))
            //invalidDate = dateInvalid(personaVM.fechaNacimiento)
        }, year, month, day
    )

    var personaSelected by remember { mutableStateOf("Cliente") }
    var expanded by remember { mutableStateOf(false) }


    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Registro de Prestamos") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
//                if (canSave) {
                viewModel.save()
//                    onNavigateBack()
//                } else {
//                    Toast.makeText(context,
//                        "No se puede guardar con un campo invalido",
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
            }) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Guardar")
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            //fecha actual
            item {
                OutlineTextFieldWithErrorView(
                    label = { Text(text = "Fecha") },
                    value = fecha,
                    onValueChange = {},
                    enabled = false, readOnly = true,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = null,
                            modifier = Modifier
                                .size(30.dp, 30.dp),
                            tint = MaterialTheme.colors.onSurface
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }
            //Fecha vencimiento
            item {
                OutlineTextFieldWithErrorView(
                    label = { Text(text = "Fecha vencimiento") },
                    value = fechaVencimiento,
                    onValueChange = {},
                    enabled = false,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = null,
                            modifier = Modifier
                                .clickable {
                                    datePicker.show()
                                    //gotFocusDate = true
                                }
                                .size(30.dp, 30.dp),
                            tint = MaterialTheme.colors.onSurface
                        )
                    },
                    errorMsg = "*Introduzca una fecha valida*",
                    //isError = invalidDate && (gotFocusDate || allVerified),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .clickable {
                            datePicker.show()
                            //gotFocusDate = true
                        }
                )
            }
            //persona
            item {
                Row {
                    Column {
                        OutlineTextFieldWithErrorView(
                            value = personaSelected,
                            onValueChange = {
                                personaSelected = it
                            },
                            enabled = false, readOnly = true,
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = null
                                )
                            },
                            modifier = Modifier
                                .clickable {
                                    expanded = true
                                    //gotFocusOcupacion = true
                                },
                            errorMsg = "*Seleccione un cliente*",
                            //isError = invalidOcupacion && (gotFocusOcupacion || allVerified)
                        )
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier
                                .padding(horizontal = 8.dp)

                        ) {
                            viewModel.uiState.personas.forEach { persona ->
                                DropdownMenuItem(
                                    onClick = {
                                        expanded = false
                                        //invalidOcupacion = false
                                        personaSelected = persona.nombres
                                        viewModel.setPersona(persona.id)
                                    },
                                    modifier = Modifier
                                        .padding(horizontal = 8.dp)
                                ) {
                                    Text(text = persona.nombres)
                                }
                            }
                        }
                    }
                    Button(
                        onClick = addPersonaClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 12.dp, top = 5.dp)
                    ) {
                        Text(text = "Add", style = MaterialTheme.typography.button)
                    }
                }
            }
            //Concepto
            item {
                OutlineTextFieldWithErrorView(
                    label = { Text("Concepto") },
                    value = viewModel.uiState.concepto,
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            //Monto
            item {
                OutlineTextFieldWithErrorView(
                    label = { Text("Monto") },
                    value = viewModel.uiState.monto,
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            //Monto
            item {
                OutlineTextFieldWithErrorView(
                    label = { Text("Balance") },
                    value = viewModel.uiState.balance,
                    onValueChange = {},
                    readOnly = true, enabled = false,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}

/* TODO:
2. Agregar Campo Balance en Personas. "cada vez que se afecte un prestamo el campo BALANCE en personas debe quedar correcto"
3. Crear la Consulta de Prestamos.
*/