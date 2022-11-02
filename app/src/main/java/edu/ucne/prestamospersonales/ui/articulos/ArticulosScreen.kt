package edu.ucne.prestamospersonales.ui.articulos

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.ucne.prestamospersonales.ui.components.RegistroBottomBar
import edu.ucne.prestamospersonales.ui.components.StyledTopBar
import edu.ucne.prestamospersonales.ui.components.TopBarStyle

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ArticulosScreen(
    articuloId: Int = 0,
    onNavigateBack: () -> Unit,
    viewModel: ArticulosViewModel = hiltViewModel(),
) {
    remember(articuloId) {
        viewModel.findById(articuloId)
        0
    }

    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current
    Scaffold(
        topBar = {
            StyledTopBar(
                style = TopBarStyle.BackTitle,
                title = "Registro de Articulos",
                onBackClick = onNavigateBack
            )
        },
        isFloatingActionButtonDocked = true,
        bottomBar = {
            RegistroBottomBar(
                onNuevoClick = { viewModel.cleanFields() },
                onEliminarClick = {
                    viewModel.delete()
                    onNavigateBack()
                }
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    Toast.makeText(
                        context,
                        viewModel.save(),
                        Toast.LENGTH_LONG
                    ).show()
                    if (viewModel.canSave()) onNavigateBack()
                },
                modifier = Modifier.size(75.dp)
            ) {
                Icon(Icons.Default.Save, "Guardar", modifier = Modifier.size(40.dp))
            }
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            OutlinedTextField(
                label = { Text("Descripción") },
                value = uiState.value.descripcion,
                onValueChange = { viewModel.setDescripcion(it) },
                isError = uiState.value.descripcionError != null,
                keyboardOptions = KeyboardOptions.Default.copy(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, top = 5.dp, bottom = 5.dp)
            )
            if (uiState.value.descripcionError != null) {
                Text(
                    text = uiState.value.descripcionError!!,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.padding(5.dp)
                )
            }

            OutlinedTextField(
                label = { Text("Marca") },
                value = uiState.value.marca,
                onValueChange = { viewModel.setMarca(it) },
                isError = uiState.value.marcaError != null,
                keyboardOptions = KeyboardOptions.Default.copy(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, top = 5.dp, bottom = 5.dp)
            )
            if (uiState.value.marcaError != null) {
                Text(
                    text = uiState.value.marcaError!!,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.padding(5.dp)
                )
            }

            OutlinedTextField(
                label = { Text("Existencia") },
                value = uiState.value.existencia,
                onValueChange = { viewModel.setExistencia(it) },
                isError = uiState.value.existenciaError != null,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, top = 5.dp, bottom = 5.dp)
            )
            if (uiState.value.existenciaError != null) {
                Text(
                    text = uiState.value.existenciaError!!,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
    }
}