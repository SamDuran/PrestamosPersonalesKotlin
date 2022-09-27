package edu.ucne.prestamospersonales.ui.ocupacion

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
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
import edu.ucne.prestamospersonales.ui.components.OutlinedTextFieldWithErrorView
import edu.ucne.prestamospersonales.ui.components.RegistroBottomBar
import edu.ucne.prestamospersonales.ui.components.StyledTopBar
import edu.ucne.prestamospersonales.ui.components.TopBarStyles

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun OcupacionScreen(
    ocupacionId:Int= 0,
    onNavigateBack: () -> Unit,
    viewModel: OcupacionViewModel = hiltViewModel(),
) {
    remember(ocupacionId) {
        viewModel.findById(ocupacionId)
        0
    }
    val focusManager = LocalFocusManager.current

    val interactionDescSource = remember { MutableInteractionSource() }
    val descFocusInteraction by interactionDescSource.collectIsFocusedAsState()
    var descFieldGotFocus by remember { mutableStateOf(false) }
    if (descFocusInteraction) descFieldGotFocus = true

    val interactionSalarioSource = remember { MutableInteractionSource() }
    val salarioFocusInteraction by interactionSalarioSource.collectIsFocusedAsState()
    var salarioFieldGotFocus by remember { mutableStateOf(false) }
    if (salarioFocusInteraction) salarioFieldGotFocus = true

    var canSave by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var allVerified by remember {mutableStateOf(false)}

    Scaffold(
        topBar = {
            StyledTopBar(
                style = TopBarStyles.BackTitle,
                title = "Registro de Ocupaciones",
                onBackClick = onNavigateBack
            )
        },
        isFloatingActionButtonDocked = true,
        bottomBar = {
            RegistroBottomBar(
                onNuevoClick = {
                    viewModel.setNew()
                    descFieldGotFocus = false
                    salarioFieldGotFocus = false
                    allVerified=false
                    focusManager.clearFocus()
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
                    viewModel.save()
                    onNavigateBack()
                } else {
                    Toast.makeText(context,
                        "No se puede guardar con un campo invalido",
                        Toast.LENGTH_LONG
                    ).show()
                }
            },
                modifier = Modifier.size(75.dp)
            ){
                Icon(Icons.Default.Save, "Guardar",modifier = Modifier.size(40.dp))
            }
        }
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {

            canSave = viewModel.uiState.descripcion.length > 3
                    && (viewModel.uiState.salario.toFloatOrNull()?:0.0f) > 14500f

            OutlinedTextFieldWithErrorView(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Descripción") },
                value = viewModel.uiState.descripcion,
                interactionSource = interactionDescSource,
                onValueChange = { viewModel.setDescripcion(it) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                errorMsg="*Ingrese una descripción valida (Campo obligatorio)*",
                isError = viewModel.uiState.descripcion.length<4 && (descFieldGotFocus || allVerified)
            )
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextFieldWithErrorView(
                modifier = Modifier.fillMaxWidth(),
                interactionSource = interactionSalarioSource,
                label = { Text(text = "Salario (RD$)") },
                value = viewModel.uiState.salario,
                onValueChange = { viewModel.setSalario(it) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        if (canSave) {
                            viewModel.save()
                            onNavigateBack()
                        } else {
                            Toast.makeText(context,
                                "No se puede guardar con un campo invalido",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                ),
                errorMsg="*Ingrese un monto de salario valido (Campo obligatorio)*",
                isError = (viewModel.uiState.salario.toFloatOrNull()?:0.0f) < 14500f && (salarioFieldGotFocus || allVerified)
            )
        }
    }
}


