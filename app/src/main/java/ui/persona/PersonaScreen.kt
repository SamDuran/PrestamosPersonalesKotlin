@file:Suppress("IMPLICIT_CAST_TO_ANY")

package ui.persona

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ui.ocupacion_list.OcupacionListViewModel
import util.*
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PersonaScreen(
    onNavigateBack: () -> Unit,
    AddOcupacionClick: () -> Unit,
    ocupacionVM: OcupacionListViewModel = hiltViewModel(),
    personaVM: PersonaViewModel = hiltViewModel()
) {
    val focusManager = LocalFocusManager.current
    var allVerified by remember { mutableStateOf(false)}

    var invalidName by remember { mutableStateOf(true) }
    var invalidTel by remember { mutableStateOf(true) }
    var invalidCel by remember { mutableStateOf(true) }
    var invalidEmail by remember { mutableStateOf(true) }
    var invalidDirection by remember { mutableStateOf(true) }
    var invalidDate by remember { mutableStateOf(true) }
    var invalidOcupacion by remember { mutableStateOf(true) }

    val c = Calendar.getInstance()

    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)

    var canSave by remember { mutableStateOf(false) }

    val context = LocalContext.current

    var date by remember { mutableStateOf("") }
    @Suppress("DEPRECATION") val datePickerDialog = DatePickerDialog(
        context, { _, year1, month1, day1 ->
            val month2: Int = month1 + 1
            //onValueChange de fecha nacimiento
            date = "$day1 - $month2 - $year1"
            personaVM.fechaNacimiento = Date(year1, month2, day1)
            invalidDate = dateInvalid(personaVM.fechaNacimiento)
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
                allVerified = true
                if (canSave) {
                    personaVM.save()
                    onNavigateBack()
                } else {
                    Toast.makeText(
                        context,
                        "No se puede guardar con un campo invalido",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Guardar")
            }
        }
    ) {

        var ocupacionSelected by remember { mutableStateOf("Ocupación") }
        var expanded by remember { mutableStateOf(false) }
        val uiState by ocupacionVM.uiState.collectAsState()
        val ocupaciones = uiState.ocupaciones

        val interactionNameSource = remember { MutableInteractionSource() }
        val nameFocusInteraction by interactionNameSource.collectIsFocusedAsState()
        var nameFieldGotFocus by remember {mutableStateOf(false)}
        if(nameFocusInteraction) nameFieldGotFocus = true

        val interactionTelSource = remember { MutableInteractionSource() }
        val telFocusInteraction by interactionTelSource.collectIsFocusedAsState()
        var telFieldGotFocus by remember {mutableStateOf(false)}
        if(telFocusInteraction) telFieldGotFocus = true

        val interactionCelSource = remember { MutableInteractionSource() }
        val celFocusInteraction by interactionCelSource.collectIsFocusedAsState()
        var celFieldGotFocus by remember {mutableStateOf(false)}
        if(celFocusInteraction) celFieldGotFocus = true

        val interactionEmailSource = remember { MutableInteractionSource() }
        val emailFocusInteraction by interactionEmailSource.collectIsFocusedAsState()
        var emailFieldGotFocus by remember {mutableStateOf(false)}
        if(emailFocusInteraction) emailFieldGotFocus = true

        val interactionDirectionSource = remember { MutableInteractionSource() }
        val directionFocusInteraction by interactionDirectionSource.collectIsFocusedAsState()
        var directionFieldGotFocus by remember {mutableStateOf(false)}
        if(directionFocusInteraction) directionFieldGotFocus = true

        var gotFocusDate by remember { mutableStateOf(false) }
        var gotFocusOcupacion by remember { mutableStateOf(false) }

        canSave = !invalidName
                && !invalidTel
                && !invalidCel
                && !invalidEmail
                && !invalidDirection
                && !invalidDate
                && !invalidOcupacion

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            item {
                OutlineTextFieldWithErrorView(
                    label = { Text(text = "Nombres") },
                    value = personaVM.nombres,
                    interactionSource = interactionNameSource,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions =  KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            invalidName = nameInvalid(personaVM.nombres)
                        },
                    onValueChange = {
                        personaVM.nombres = it
                        invalidName = nameInvalid(personaVM.nombres)
                    },
                    errorMsg = "*Ingrese un nombre valido*",
                    isError = invalidName && (nameFieldGotFocus || allVerified)
                )
            }//Nombres
            item {
                OutlineTextFieldWithErrorView(
                    label = { Text(text = "Teléfono") },
                    value = personaVM.telefono,
                    interactionSource = interactionTelSource,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions =  KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            invalidTel = telInvalid(personaVM.telefono)
                        },
                    onValueChange = {
                        personaVM.telefono = it
                        invalidTel = telInvalid(personaVM.telefono)
                    },
                    errorMsg = "*Ingrese un número de teléfono valido*",
                    isError = invalidTel && (telFieldGotFocus || allVerified)
                )
            }//Telefono
            item {
                OutlineTextFieldWithErrorView(
                    label = { Text(text = "Celular") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions =  KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    interactionSource = interactionCelSource,
                    value = personaVM.celular,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            invalidCel = telInvalid(personaVM.celular)
                        },
                    onValueChange = {
                        personaVM.celular = it
                        invalidCel = telInvalid(personaVM.celular)
                    },
                    errorMsg = "*Ingrese un número de celular valido*",
                    isError = invalidCel && (celFieldGotFocus || allVerified)
                )
            }//Celular
            item {
                OutlineTextFieldWithErrorView(
                    label = { Text(text = "Email") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            invalidEmail = emailInvalid(personaVM.email)
                        },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions =  KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    interactionSource = interactionEmailSource,
                    value = personaVM.email,
                    onValueChange = {
                        personaVM.email = it
                        invalidEmail = emailInvalid(personaVM.email)
                    },
                    errorMsg = "*Ingrese un correo electrónico valido*",
                    isError = invalidEmail && (emailFieldGotFocus || allVerified)
                )
            }//Email
            item {
                OutlineTextFieldWithErrorView(
                    label = { Text(text = "Dirección") },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions =  KeyboardActions(
                        onNext = {
                            focusManager.clearFocus()
                            datePickerDialog.show()
                            gotFocusDate = true
                        }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            invalidDirection = nameInvalid(personaVM.direccion)
                        },
                    value = personaVM.direccion,
                    onValueChange = {
                        personaVM.direccion = it
                        invalidDirection = nameInvalid(personaVM.direccion)
                    },
                    interactionSource = interactionDirectionSource,
                    errorMsg = "*Ingrese una dirección valida*",
                    isError = invalidDirection && (directionFieldGotFocus || allVerified)
                )
            }//Direccion
            item {
                OutlineTextFieldWithErrorView(
                    label = { Text(text = "Fecha nacimiento") },
                    value = date,
                    onValueChange = {},
                    enabled = false,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = null,
                            modifier = Modifier
                                .clickable {
                                    datePickerDialog.show()
                                    gotFocusDate = true
                                }
                                .size(30.dp, 30.dp),
                            tint = MaterialTheme.colors.onSurface
                        )
                    },
                    errorMsg = "*Introduzca una fecha valida*",
                    isError = invalidDate && (gotFocusDate || allVerified),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            datePickerDialog.show()
                            gotFocusDate = true
                        }
                )
            }//Fecha Nacimiento
            item {
                Row {
                    Column {
                        OutlineTextFieldWithErrorView(
                            value = ocupacionSelected,
                            onValueChange = {
                                ocupacionSelected = it
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
                                    gotFocusOcupacion = true
                                },
                            errorMsg = "*Seleccione una ocupación*",
                            isError = invalidOcupacion && (gotFocusOcupacion || allVerified)
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
                                        invalidOcupacion = false
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

private fun nameInvalid(value: String): Boolean {
    return value.length < 2
}

private fun telInvalid(value: String): Boolean {
    for (caracter in value) if (!caracter.isDigit()) return false
    return value.length < 10
}

private fun emailInvalid(email: String): Boolean {
    val regex = Regex("[A-Za-z0-9_]{3,}@[A-Za-z0-9]{3,}(\\.[A-Za-z0-9_]{3,})(\\.[A-Za-z0-9_]{2,})*")
    return !regex.matches(email)
}

@Suppress("DEPRECATION")
private fun dateInvalid(fecha: Date): Boolean {
    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR) - 17
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)
    return fecha > Date(year, month, day) || fecha < Date(1930,1,1)
}