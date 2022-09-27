package edu.ucne.prestamospersonales.ui.prestamo

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.ucne.prestamospersonales.ui.components.*
import edu.ucne.prestamospersonales.util.DateConverter
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
    remember(prestamoId) {
        viewModel.findById(prestamoId)
        0
    }

    val context = LocalContext.current
    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)

    val focusManager = LocalFocusManager.current
    var allVerified by remember { mutableStateOf(false) }

    val interactionConceptoSource = remember { MutableInteractionSource() }
    val conceptoFocusInteraction by interactionConceptoSource.collectIsFocusedAsState()
    var conceptoGotFocus by remember { mutableStateOf(false) }
    if (conceptoFocusInteraction) conceptoGotFocus = true

    val interactionMontoSource = remember { MutableInteractionSource() }
    val montoFocusInteraction by interactionMontoSource.collectIsFocusedAsState()
    var montoGotFocus by remember { mutableStateOf(false) }
    if (montoFocusInteraction) montoGotFocus = true

    val interactionBalanceSource = remember { MutableInteractionSource() }
    val balanceFocusInteraction by interactionBalanceSource.collectIsFocusedAsState()
    var balanceGotFocus by remember { mutableStateOf(false) }
    if (balanceFocusInteraction) balanceGotFocus = true

    var gotFocusDate by remember { mutableStateOf(false) }
    var gotFocusPersona by remember { mutableStateOf(false) }

    var canSave by remember { mutableStateOf(false) }

    canSave = viewModel.fechaVencimiento.isNotEmpty()
            && (viewModel.uiState.balance.isNotEmpty() && (viewModel.uiState.balance.toDouble() > 1.0))
            && (viewModel.uiState.monto.isNotEmpty() && (viewModel.uiState.monto.toDouble() > 1.0))
            && viewModel.uiState.prestamoId != 0

    var showDatePicker by remember { mutableStateOf(false) }
    if (showDatePicker) {
        CustomDatePickerDialog(
            label = "Seleccione la fecha de vencimiento",
            onSelectDate = {
                viewModel.setFecha(it)
                viewModel.fechaVencimiento = DateConverter().toString(it)
            },
            onDismissRequest = { showDatePicker = false }
        )
    }

    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            StyledTopBar(
                style = TopBarStyles.BackTitle,
                title = "Registro de Prestamos",
                onBackClick = onNavigateBack
            )
        },
        isFloatingActionButtonDocked = true,
        bottomBar = {
            RegistroBottomBar(
                onNuevoClick = {
                    viewModel.setNew()
                    focusManager.clearFocus()
                    allVerified = false
                    showDatePicker = true
                },
                onEliminarClick = {
                    viewModel.delete()
                    onNavigateBack()
                })
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                allVerified = true
                if (canSave) {
                    viewModel.save(year, month, day)
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
                Icon(Icons.Default.Save, "Guardar", modifier = Modifier.size(40.dp))
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
                OutlinedTextFieldWithErrorView(
                    label = { Text(text = "Fecha creación") },
                    value = viewModel.fechaPrestamo,
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
                OutlinedTextFieldWithErrorView(
                    label = { Text(text = "Fecha vencimiento") },
                    value = viewModel.fechaVencimiento,
                    onValueChange = {},
                    enabled = false,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = null,
                            modifier = Modifier
                                .clickable {
                                    showDatePicker = true
                                    gotFocusDate = true
                                }
                                .size(30.dp, 30.dp),
                            tint = MaterialTheme.colors.onSurface
                        )
                    },
                    errorMsg = "*Introduzca una fecha valida*",
                    isError = viewModel.fechaVencimiento.isEmpty() && (gotFocusDate || allVerified),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .clickable {
                            showDatePicker = true
                            gotFocusDate = true
                        }
                )
            }
            //persona
            item {
                Row {
                    Column {
                        OutlinedTextFieldWithErrorView(
                            value = viewModel.personaSelected,
                            onValueChange = {
                                viewModel.personaSelected = it
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
                                    gotFocusPersona = true
                                },
                            errorMsg = "*Seleccione un cliente*",
                            isError = viewModel.uiState.personaId == 0 && (gotFocusPersona || allVerified)
                        )
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)

                        ) {
                            viewModel.uiState.personas.forEach { persona ->
                                DropdownMenuItem(
                                    onClick = {
                                        expanded = false
                                        viewModel.personaSelected = persona.nombres
                                        viewModel.setPersona(persona.id)
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 8.dp)
                                ) {
                                    Text(text = persona.nombres)
                                }
                            }
                        }
                    }
                    FloatingActionButton(
                        shape = MaterialTheme.shapes.small.copy(CornerSize(4.dp)),
                        onClick = addPersonaClick,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 12.dp)
                    ) {
                        Icon(imageVector = Icons.Outlined.Add,
                            contentDescription = "Agregar Persona"
                        )
                    }
                }
            }
            //Concepto
            item {
                OutlinedTextFieldWithErrorView(
                    label = { Text("Concepto") },
                    interactionSource = interactionConceptoSource,
                    value = viewModel.uiState.concepto,
                    onValueChange = {
                        viewModel.setConcepto(it)
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    isError = viewModel.uiState.concepto.length < 9 && (conceptoGotFocus || allVerified),
                    errorMsg = "*Ingrese un concepto válido (campo obligatorio)*"
                )
            }
            //Monto
            item {
                OutlinedTextFieldWithErrorView(
                    label = { Text("Monto") },
                    value = viewModel.uiState.monto,
                    interactionSource = interactionMontoSource,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    onValueChange = {
                        viewModel.setMonto(it)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    isError = (viewModel.uiState.monto.isEmpty() || (viewModel.uiState.monto.toDouble() < 1.0)) && (montoGotFocus || allVerified),
                    errorMsg = "*Ingrese un monto valido (campo obligatorio)*"
                )
            }
            //Balance
            item {
                OutlinedTextFieldWithErrorView(
                    label = { Text("Balance") },
                    value = viewModel.uiState.balance,
                    interactionSource = interactionBalanceSource,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    ),
                    onValueChange = {
                        viewModel.setBalance(it)
                    },
                    isError = (viewModel.uiState.balance.isEmpty() || (viewModel.uiState.balance.toDouble() < 1.0)) && (balanceGotFocus || allVerified),
                    errorMsg = "*Ingrese un balance valido (campo obligatorio)*",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
