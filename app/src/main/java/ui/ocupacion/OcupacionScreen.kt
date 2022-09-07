package ui.ocupacion

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ui.OcupacionViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun OcupacionScreen(
    onNavigateBack: () -> Unit,
    viewModel: OcupacionViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registro de Ocupaciones") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if(viewModel.salario>= 260.32 && viewModel.descripcion.length>3){
                    viewModel.Save()
                    onNavigateBack()
                }
//                        else if(viewModel.descripcion.length<=3) {
//                            //val toast= Toast.makeText(applicationContext, "",Toast.LENGTH_SHORT).show()
//                        }else {
//
//                        }
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
            val focusManager = LocalFocusManager.current
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Descripción") },
                value = viewModel.descripcion,
                onValueChange = {viewModel.descripcion = it},
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                keyboardActions =  KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    )
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Salario (USD)") },
                value = viewModel.salario.toString(),
                onValueChange = {viewModel.salario = it.toFloat()},
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions =  KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        if(viewModel.salario>= 260.32 && viewModel.descripcion.length>3){
                            viewModel.Save()
                            onNavigateBack()
                        }
//                        else if(viewModel.descripcion.length<=3) {
//                            //val toast= Toast.makeText(applicationContext, "",Toast.LENGTH_SHORT).show()
//                        }else {
//
//                        }
                    }
                )
            )
        }
    }
}