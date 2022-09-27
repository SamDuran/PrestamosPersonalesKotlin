package edu.ucne.prestamospersonales.ui.persona

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.ucne.prestamospersonales.data.local.entities.Persona
import edu.ucne.prestamospersonales.ui.components.ItemCard
import edu.ucne.prestamospersonales.ui.components.StyledTopBar
import edu.ucne.prestamospersonales.ui.components.TopBarStyles
import edu.ucne.prestamospersonales.util.DateConverter

@Composable
fun PersonaListScreen(
    onNavigationBack: () -> Unit,
    AddClick: () -> Unit,
    viewModel: PersonaListViewModel = hiltViewModel(),
    onPersonaClick: (Int) -> Unit,
) {

    Scaffold(
        topBar = {
            StyledTopBar(
                style = TopBarStyles.BackTitleFind,
                title = "Personas",
                onBackClick = onNavigationBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = AddClick) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar persona")
            }
        }
    ) {
        val uiState by viewModel.uiState.collectAsState()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
        ) {
            PersonaList(
                onPersonaClick = onPersonaClick,
                personas = uiState.personas,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
            )
        }
    }
}


@Composable
fun PersonaList(
    personas: List<Persona>,
    modifier: Modifier = Modifier,
    viewModel: PersonaListViewModel = hiltViewModel(),
    onPersonaClick: (Int) -> Unit,
) {

    LazyColumn(modifier = modifier.background(MaterialTheme.colors.background)) {
        this.items(personas) { persona ->
            viewModel.getOcupacion(persona.ocupacionId)
                ?.let {
                    PersonaRow(persona = persona, ocupacion = it) { id ->
                        onPersonaClick(id)
                    }
                }
        }
    }
}


@Composable
fun PersonaRow(
    persona: Persona,
    ocupacion: String,
    modifier: Modifier = Modifier,
    onPersonaClick: (Int) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    ItemCard(
        modifier = modifier
            .padding(8.dp)
            .clickable(onClick = { onPersonaClick(persona.id) })
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp)
        ) {
            Text(
                text = persona.nombres,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(horizontal = 5.dp)
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = ocupacion + " ${persona.balance}",
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.padding(5.dp)
                )
                Text(
                    text = "Email: ${persona.email}",
                    modifier = Modifier.padding(horizontal = 5.dp)
                )
            }
            if (expanded) {
                ExtraInformation(persona = persona)
            }
            ExpandButton(
                expanded = expanded,
                onClick = { expanded = !expanded }
            )

        }
    }
}

@Composable
fun ExpandButton(
    expanded: Boolean,
    onClick: () -> Unit,
) {
    Box(contentAlignment = Center, modifier = Modifier.fillMaxWidth()) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                tint = MaterialTheme.colors.onSurface,
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun ExtraInformation(
    persona: Persona,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Tel: ${persona.telefono}", modifier = Modifier.padding(horizontal = 5.dp))
            Text(text = "Cel: ${persona.celular}", modifier = Modifier.padding(horizontal = 5.dp))
        }
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Nacimiento: ${DateConverter().toString(persona.fechaNacimiento)}",
                modifier = Modifier.padding(horizontal = 5.dp))
            Text(text = "Direccion: ${persona.direccion}",
                modifier = Modifier.padding(horizontal = 5.dp))
        }
    }
}

