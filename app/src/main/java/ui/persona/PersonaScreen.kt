package ui.persona

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ui.ocupacion_list.OcupacionListViewModel
import util.Converters
import util.DateConverter
import util.OutlineTextFieldWithErrorView
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PersonaScreen(
    onNavigateBack: () -> Unit,
    AddOcupacionClick: () -> Unit,
    ocupacionVM: OcupacionListViewModel = hiltViewModel(),
    personaVM: PersonaViewModel = hiltViewModel()
) {
    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)

    val context = LocalContext.current

    var date by remember {mutableStateOf("")}
    val datePickerDialog = DatePickerDialog(
        context, { d, year1, month1, day1 ->
            val month = month1 + 1
            date = "$day1 - $month - $year1"
            personaVM.fechaNacimiento = Date(year1,month,day1)
        }, year, month, day
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registro de Personas") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                personaVM.save()
                onNavigateBack()
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Guardar")
            }
        }
    ) {
        var ocupacionSelected by remember { mutableStateOf("Ocupación") }
        var expanded by remember { mutableStateOf(false) }
        val uiState by ocupacionVM.uiState.collectAsState()
        val ocupaciones = uiState.ocupaciones

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            item {
                OutlinedTextField(
                    label = { Text(text = "Nombres") },
                    modifier = Modifier.fillMaxWidth(),
                    value = personaVM.nombres,
                    onValueChange = { personaVM.nombres = it }
                )
            }//Nombres
            item {
                OutlinedTextField(
                    label = { Text(text = "Teléfono") },
                    modifier = Modifier.fillMaxWidth(),
                    value = personaVM.telefono,
                    onValueChange = { personaVM.telefono = it }
                )
            }//Telefono
            item {
                OutlinedTextField(
                    label = { Text(text = "Celular") },
                    modifier = Modifier.fillMaxWidth(),
                    value = personaVM.celular,
                    onValueChange = { personaVM.celular = it }
                )
            }//Celular
            item {
                OutlinedTextField(
                    label = { Text(text = "Email") },
                    modifier = Modifier.fillMaxWidth(),
                    value = personaVM.email,
                    onValueChange = { personaVM.email = it }
                )
            }//Email
            item {
                OutlinedTextField(
                    label = { Text(text = "Dirección") },
                    modifier = Modifier.fillMaxWidth(),
                    value = personaVM.direccion,
                    onValueChange = { personaVM.direccion = it }
                )
            }//Direccion
            item {
                OutlineTextFieldWithErrorView(
                    label = { Text(text = "Fecha nacimiento") },
                    value = DateConverter().toString(personaVM.fechaNacimiento),
                    enabled = false,
                    trailingIcon={Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        modifier = Modifier
                            .clickable{datePickerDialog.show()}
                            .size(30.dp, 30.dp),
                        tint = MaterialTheme.colors.onSurface
                    )},
                    onValueChange = { personaVM.fechaNacimiento = DateConverter().stringToDate(it)!! },
                    errorMsg = "Introduzca una fecha valida",
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable{datePickerDialog.show()}
                )
            }//Fecha Nacimiento
            item {
                Row {
                    Column {
                        OutlinedTextField(
                            trailingIcon = {Icons.Filled.ArrowDropDown},
                            value = ocupacionSelected,
                            onValueChange = { ocupacionSelected = it },
                            enabled = false, readOnly = true,
                            modifier = Modifier
                                .clickable { expanded = true }
                        )
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier
                                .padding(horizontal = 8.dp)

                        ) {
                            ocupaciones.forEach { ocupacion ->
                                DropdownMenuItem(
                                    onClick = {
                                        expanded = false
                                        ocupacionSelected = ocupacion.descripcion
                                        personaVM.ocupacionId = ocupacion.id
                                    },
                                    modifier = Modifier
                                        .padding(horizontal = 8.dp)
                                ) {
                                    Text(text = ocupacion.descripcion)
                                }
                            }
                        }
                    }
                    Button(
                        onClick = AddOcupacionClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 12.dp, top = 5.dp)
                    ) {
                        Text(text = "New", style = MaterialTheme.typography.button)
                    }
                }

            }//Ocupacion
        }
    }
}

