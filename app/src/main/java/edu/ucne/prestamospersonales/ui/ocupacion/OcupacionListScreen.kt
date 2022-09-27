package edu.ucne.prestamospersonales.ui.ocupacion

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.ucne.prestamospersonales.data.local.entities.Ocupacion
import edu.ucne.prestamospersonales.ui.components.ItemCard
import edu.ucne.prestamospersonales.ui.components.StyledTopBar
import edu.ucne.prestamospersonales.ui.components.TopBarStyles

@Composable
fun OcupacionListScreen(
    onBackClick: () -> Unit,
    AddClick: () -> Unit,
    viewModel: OcupacionListViewModel = hiltViewModel(),
    onItemClick: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            StyledTopBar(
                style = TopBarStyles.BackTitleFind,
                title = "Ocupaciones",
                onBackClick = onBackClick
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = AddClick) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar Ocupación")
            }
        }
    ) {
        val uiState by viewModel.uiState.collectAsState()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
        ) {

            OcupacionList(
                onItemClick = onItemClick,
                ocupaciones = uiState.ocupaciones,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            )
        }
    }
}

@Composable
fun OcupacionList(
    ocupaciones: List<Ocupacion>,
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit
) {
    LazyColumn(modifier = modifier.background(MaterialTheme.colors.background)) {
        this.items(ocupaciones) { ocupacion ->
            OcupacionRow(ocupacion){ocupacionId ->
                onItemClick(ocupacionId)
            }
        }
    }
}

@Composable
fun OcupacionRow(
    ocupacion: Ocupacion,
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit
) {
    ItemCard(
        modifier = modifier
            .padding(8.dp)
            .clickable(onClick = {
                onItemClick(ocupacion.id)
            })
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            Text(
                text = ocupacion.descripcion,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(horizontal = 5.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Salario: RD\$ ${ocupacion.salario}",
                    modifier = Modifier.padding( 5.dp)
                )
            }
        }
    }
}
