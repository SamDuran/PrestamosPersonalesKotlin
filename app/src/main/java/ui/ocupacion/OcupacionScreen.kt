package ui.ocupacion

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
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
import util.OutlineTextFieldWithErrorView

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun OcupacionScreen(
    onNavigateBack: () -> Unit,
    viewModel: OcupacionViewModel = hiltViewModel(),
) {
    var canSave by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var allVerified by remember {mutableStateOf(false)}

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registro de Ocupaciones") }
            )
        },
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
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Guardar")
            }
        }
    )
    {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {

            canSave = !descInvalid(viewModel.descripcion)
                    && !salarioInvalid(viewModel.salario)

            val focusManager = LocalFocusManager.current

            val interactionDescSource = remember { MutableInteractionSource() }
            val descFocusInteraction by interactionDescSource.collectIsFocusedAsState()
            var descFieldGotFocus by remember { mutableStateOf(false) }
            if (descFocusInteraction) descFieldGotFocus = true

            val interactionSalarioSource = remember { MutableInteractionSource() }
            val salarioFocusInteraction by interactionSalarioSource.collectIsFocusedAsState()
            var salarioFieldGotFocus by remember { mutableStateOf(false) }
            if (salarioFocusInteraction) salarioFieldGotFocus = true

            OutlineTextFieldWithErrorView(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Descripción") },
                value = viewModel.descripcion,
                interactionSource = interactionDescSource,
                onValueChange = { viewModel.descripcion = it },
                keyboardOptions = KeyboardOptions.Default.copy(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                errorMsg="*Ingrese una descripción valida*",
                isError = descInvalid(viewModel.descripcion) && (descFieldGotFocus || allVerified)
            )
            Spacer(modifier = Modifier.height(20.dp))

            OutlineTextFieldWithErrorView(
                modifier = Modifier.fillMaxWidth(),
                interactionSource = interactionSalarioSource,
                label = { Text(text = "Salario (RD$)") },
                value = viewModel.salario.toString(),
                onValueChange = { viewModel.salario = it.toFloat() },
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
                errorMsg="*Ingrese un monto de salario valido*",
                isError = salarioInvalid(viewModel.salario) && (salarioFieldGotFocus || allVerified)
            )
        }
    }
}

private fun descInvalid(value: String): Boolean {
    return value.length < 4
}

private fun salarioInvalid(value: Float): Boolean {
    return value < 14500f
}