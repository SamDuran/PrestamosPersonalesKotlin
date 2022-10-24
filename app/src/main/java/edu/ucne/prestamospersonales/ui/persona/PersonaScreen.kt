package edu.ucne.prestamospersonales.ui.persona

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.ucne.prestamospersonales.ui.components.*
import edu.ucne.prestamospersonales.ui.ocupacion.OcupacionListViewModel
import edu.ucne.prestamospersonales.util.birthDateNoValid
import edu.ucne.prestamospersonales.util.emailNoValid
import edu.ucne.prestamospersonales.util.phoneNumberNoValid
import edu.ucne.prestamospersonales.util.veryShortName

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PersonaScreen(
    personaId: Int = 0,
    onNavigateBack: () -> Unit,
    AddOcupacionClick: () -> Unit,
    ocupacionVM: OcupacionListViewModel = hiltViewModel(),
    personaVM: PersonaViewModel = hiltViewModel(),
) {
    remember(personaId) {
        personaVM.find(personaId)
        0
    }

    val focusManager = LocalFocusManager.current
    var allVerified by remember { mutableStateOf(false) }

    var canSave by remember { mutableStateOf(false) }

    val context = LocalContext.current

    var showDatePicker by remember {mutableStateOf(false)}
    if(showDatePicker) {
        CustomDatePickerDialog(
            label = "Seleccione la fecha de nacimiento",
            onSelectDate = {personaVM.setFecha(it)},
            onDismissRequest = {showDatePicker=false}
        )
    }

    Scaffold(
        topBar = {
            StyledTopBar(
                style = TopBarStyle.BackTitle,
                title = "Registro de Personas",
                onBackClick = onNavigateBack
            )
        },
        isFloatingActionButtonDocked = true,
        bottomBar = {
            RegistroBottomBar(
                onNuevoClick = {
                    personaVM.setNew()
                    allVerified = false
                    focusManager.clearFocus()
                },
                onEliminarClick = {
                    personaVM.delete()
                    onNavigateBack()
                })
        },
        floatingActionButtonPosition = FabPosition.Center,
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
            },
                modifier = Modifier.size(75.dp)
            ) {
                Icon(imageVector = Icons.Default.Save,
                    contentDescription = "Guardar",
                    modifier = Modifier.size(40.dp))
            }
        }
    ) {

        var expanded by remember { mutableStateOf(false) }
        val uiState by ocupacionVM.uiState.collectAsState()
        val ocupaciones = uiState.ocupaciones

        val interactionNameSource = remember { MutableInteractionSource() }
        val nameFocusInteraction by interactionNameSource.collectIsFocusedAsState()
        var nameFieldGotFocus by remember { mutableStateOf(false) }
        if (nameFocusInteraction) nameFieldGotFocus = true

        val interactionTelSource = remember { MutableInteractionSource() }
        val telFocusInteraction by interactionTelSource.collectIsFocusedAsState()
        var telFieldGotFocus by remember { mutableStateOf(false) }
        if (telFocusInteraction) telFieldGotFocus = true

        val interactionCelSource = remember { MutableInteractionSource() }
        val celFocusInteraction by interactionCelSource.collectIsFocusedAsState()
        var celFieldGotFocus by remember { mutableStateOf(false) }
        if (celFocusInteraction) celFieldGotFocus = true

        val interactionEmailSource = remember { MutableInteractionSource() }
        val emailFocusInteraction by interactionEmailSource.collectIsFocusedAsState()
        var emailFieldGotFocus by remember { mutableStateOf(false) }
        if (emailFocusInteraction) emailFieldGotFocus = true

        val interactionDirectionSource = remember { MutableInteractionSource() }
        val directionFocusInteraction by interactionDirectionSource.collectIsFocusedAsState()
        var directionFieldGotFocus by remember { mutableStateOf(false) }
        if (directionFocusInteraction) directionFieldGotFocus = true

        var gotFocusDate by remember { mutableStateOf(false) }
        var gotFocusOcupacion by remember { mutableStateOf(false) }

        canSave = !veryShortName(personaVM.uiState.nombres)
                && !phoneNumberNoValid(personaVM.uiState.telefono)
                && !phoneNumberNoValid(personaVM.uiState.celular)
                && !emailNoValid(personaVM.uiState.email)
                && !veryShortName(personaVM.uiState.direccion)
                && !birthDateNoValid(personaVM.uiState.fechaNacimiento)
                && personaVM.uiState.ocupacionId!=0

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            item {
                OutlinedTextFieldWithErrorView(
                    label = { Text(text = "Nombres") },
                    value = personaVM.uiState.nombres,
                    interactionSource = interactionNameSource,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    modifier = Modifier
                        .fillMaxWidth(),
                    onValueChange = {
                        personaVM.setNombres(it)
                    },
                    errorMsg = "*Ingrese un nombre valido*",
                    isError = veryShortName(personaVM.uiState.nombres) && (nameFieldGotFocus || allVerified)
                )
            }//Nombres
            item {
                OutlinedTextFieldWithErrorView(
                    label = { Text(text = "Teléfono") },
                    value = personaVM.uiState.telefono,
                    interactionSource = interactionTelSource,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    modifier = Modifier
                        .fillMaxWidth(),
                    onValueChange = {
                        personaVM.setTelefono(it)
                    },
                    errorMsg = "*Ingrese un número de teléfono valido*",
                    isError = phoneNumberNoValid(personaVM.uiState.telefono) && (telFieldGotFocus || allVerified)
                )
            }//Telefono
            item {
                OutlinedTextFieldWithErrorView(
                    label = { Text(text = "Celular") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    interactionSource = interactionCelSource,
                    value = personaVM.uiState.celular,
                    modifier = Modifier
                        .fillMaxWidth(),
                    onValueChange = {
                        personaVM.setCelular(it)
                    },
                    errorMsg = "*Ingrese un número de celular valido*",
                    isError = phoneNumberNoValid(personaVM.uiState.celular) && (celFieldGotFocus || allVerified)
                )
            }//Celular
            item {
                OutlinedTextFieldWithErrorView(
                    label = { Text(text = "Email") },
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    interactionSource = interactionEmailSource,
                    value = personaVM.uiState.email,
                    onValueChange = {
                        personaVM.setEmail(it)
                    },
                    errorMsg = "*Ingrese un correo electrónico valido*",
                    isError = emailNoValid(personaVM.uiState.email) && (emailFieldGotFocus || allVerified)
                )
            }//Email
            item {
                OutlinedTextFieldWithErrorView(
                    label = { Text(text = "Dirección") },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.clearFocus()
                            showDatePicker = true
                            gotFocusDate = true
                        }
                    ),
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = personaVM.uiState.direccion,
                    onValueChange = {
                        personaVM.setDireccion(it)
                    },
                    interactionSource = interactionDirectionSource,
                    errorMsg = "*Ingrese una dirección valida*",
                    isError = veryShortName(personaVM.uiState.direccion) && (directionFieldGotFocus || allVerified)
                )
            }//Direccion
            item {
                OutlinedTextFieldWithErrorView(
                    label = { Text(text = "Fecha nacimiento") },
                    value = personaVM.fechaNacimiento,
                    onValueChange = {
                        personaVM.fechaNacimiento = it
                    },
                    enabled = false,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = null,
                            modifier = Modifier
                                .clickable {
                                    //datePickerDialog.show()
                                    showDatePicker = true
                                    gotFocusDate = true
                                }
                                .size(30.dp, 30.dp),
                            tint = MaterialTheme.colors.onSurface
                        )
                    },
                    errorMsg = "*Introduzca una fecha valida*",
                    isError = birthDateNoValid(personaVM.uiState.fechaNacimiento) && (gotFocusDate || allVerified),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            //datePickerDialog.show()
                            showDatePicker = true
                            gotFocusDate = true
                        }
                )
            }//Fecha Nacimiento
            item {
                Row {
                    Column {
                        OutlinedTextFieldWithErrorView(
                            value = personaVM.ocupacionSelected,
                            onValueChange = {
                                personaVM.ocupacionSelected = it
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
                            isError = personaVM.uiState.ocupacionId ==0 && (gotFocusOcupacion || allVerified)
                        )
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)

                        ) {
                            ocupaciones.forEach { ocupacion ->
                                DropdownMenuItem(
                                    onClick = {
                                        expanded = false
                                        personaVM.ocupacionSelected = ocupacion.descripcion
                                        personaVM.setOcupacion(ocupacion.id)
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 8.dp)
                                ) {
                                    Text(text = ocupacion.descripcion, textAlign = TextAlign.Center, color= Color.White)
                                }
                            }
                        }
                    }
                    FloatingActionButton(
                        shape = MaterialTheme.shapes.small.copy(CornerSize(4.dp)),
                        onClick = AddOcupacionClick,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 12.dp)
                    ) {
                        Icon(imageVector = Icons.Outlined.Add,
                            contentDescription = "Agregar Persona"
                        )
                    }
                }

            }//Ocupacion
            item {
                if (personaVM.uiState.balance > 0) {
                    OutlinedTextFieldWithErrorView(
                        label = { Text(text = "Balance") },
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = personaVM.uiState.balance.toString(),
                        onValueChange = { },
                        readOnly = true, enabled = false
                    )
                }
            }//balance
        }
    }
}
