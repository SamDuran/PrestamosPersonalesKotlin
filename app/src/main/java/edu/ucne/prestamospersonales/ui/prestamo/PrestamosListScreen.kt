package edu.ucne.prestamospersonales.ui.prestamo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.ucne.prestamospersonales.data.local.entities.Prestamo
import edu.ucne.prestamospersonales.ui.components.ItemCard
import edu.ucne.prestamospersonales.ui.components.StyledTopBar
import edu.ucne.prestamospersonales.ui.components.TopBarStyle
import edu.ucne.prestamospersonales.ui.persona.ExpandButton
import edu.ucne.prestamospersonales.ui.theme.bgVariant1
import edu.ucne.prestamospersonales.ui.theme.bgVariant2
import edu.ucne.prestamospersonales.util.DateConverter
import edu.ucne.prestamospersonales.util.filtersPrestamo


@Composable
fun PrestamosListScreen(
    onNavigateBack: () -> Unit,
    addPrestamoClick: () -> Unit,
    viewModel: PrestamosListViewModel = hiltViewModel(),
    onItemClick: (Int) -> Unit,
) {
    Scaffold(
        topBar = {
            StyledTopBar(
                style = TopBarStyle.BackTitleFind,
                title = "Prestamos",
                onBackClick = onNavigateBack,
                filtros = filtersPrestamo
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = addPrestamoClick) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar prestamo")
            }
        }
    ) {
        val uiState by viewModel.uiState.collectAsState()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
        ) {
            PrestamoListBody(prestamos = uiState.prestamos, modifier = Modifier
                .fillMaxSize()
                .padding(it), onItemClick = onItemClick)
        }
    }
}

@Composable
fun PrestamoListBody(
    prestamos: List<Prestamo>,
    modifier: Modifier = Modifier,
    viewModel: PrestamosListViewModel = hiltViewModel(),
    onItemClick: (Int) -> Unit,
) {

    LazyColumn(
        modifier = modifier
            .background(brush = Brush.verticalGradient(
                colors = listOf(bgVariant1(), bgVariant2())))
    ) {
        this.items(prestamos) { prestamo ->
            val ocupacion = viewModel.getOcupacion(prestamo) ?: ""
            val persona = viewModel.getPersona(prestamo.personaId)

            PrestamoRow(prestamo = prestamo,
                persona = persona,
                ocupacion = ocupacion
            ) { id ->
                onItemClick(id)
            }
        }
    }
}

@Composable
fun PrestamoRow(
    prestamo: Prestamo,
    persona: String,
    ocupacion: String,
    onItemClick: (Int) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    ItemCard(
        modifier = Modifier.padding(8.dp)
            .clickable(onClick = { onItemClick(prestamo.prestamoId) })
    ) {
        Text(
            text = "$ocupacion $persona",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h6,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Monto: ${prestamo.monto}",
                    modifier = Modifier.padding(horizontal = 5.dp)
                )
                Text(
                    text = "Balance: ${prestamo.balance}",
                    modifier = Modifier.padding(horizontal = 5.dp)
                )
            }

        }
        if (expanded) {
            ExtraInformation(prestamo)
        }
        ExpandButton(
            expanded = expanded,
            onClick = { expanded = !expanded }
        )
    }
}

@Composable
private fun ExtraInformation(
    prestamo: Prestamo,
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Creación: ${DateConverter().toString(prestamo.fecha)}",
                modifier = Modifier.padding(horizontal = 5.dp)
            )
            Text(
                text = "Vence: ${DateConverter().toString(prestamo.vence)}",
                modifier = Modifier.padding(horizontal = 5.dp)
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Concepto",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h6,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp))
            Text(text = prestamo.concepto,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp))
        }
    }
}


