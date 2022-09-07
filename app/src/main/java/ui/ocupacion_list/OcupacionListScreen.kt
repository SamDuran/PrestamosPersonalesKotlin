package ui.ocupacion_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import data.entities.Ocupacion

@Composable
fun OcupacionListScreen(
    onClick: () -> Unit,
    viewModel : OcupacionListViewModel = hiltViewModel()
){
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Lista de Ocupaciones") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onClick) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar Ocupación")
            }
        }
    ) {
        val uiState by viewModel.uiState.collectAsState()
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(it)) {

            StudentList(
                ocupaciones = uiState.ocupaciones,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            )
        }
    }
}

@Composable
fun StudentList(
    ocupaciones: List<Ocupacion>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.background(MaterialTheme.colors.background)) {
        this.items(ocupaciones) { ocupacion ->
            OcupacionRow(ocupacion)
        }
    }
}

@Composable
fun OcupacionRow(
    ocupacion: Ocupacion,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = 7.dp,
        modifier = modifier
            .padding(8.dp)
            .background(MaterialTheme.colors.surface)
            .clip(RoundedCornerShape(percent= 20))
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal=10.dp)
        ) {
            Text(
                text = ocupacion.descripcion,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(horizontal = 5.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Salario: \$USD ${ocupacion.salario}",
                    modifier = Modifier.padding(horizontal = 5.dp)
                )
            }
        }
    }
}
