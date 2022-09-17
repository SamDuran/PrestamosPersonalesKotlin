package ui.persona_list

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import data.entities.Ocupacion
import data.entities.Persona
import util.DateConverter

@Composable
fun PersonaListScreen (
    AddClick: ()->Unit,
    viewModel: PersonaListViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Lista de Personas")})
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
                personas = uiState.personas,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            )
        }
    }
}


@Composable
fun PersonaList(
    personas : List<Persona>,
    modifier: Modifier = Modifier,
    viewModel: PersonaListViewModel = hiltViewModel()
) {

    LazyColumn(modifier = modifier.background(MaterialTheme.colors.background)) {
        this.items(personas) { persona ->
            viewModel.getOcupacion(persona.ocupacionId)
            PersonaRow(persona = persona, ocupacion = viewModel.ocupacion )
        }
    }
}


@Composable
fun PersonaRow(
    persona: Persona,
    ocupacion : Ocupacion,
    modifier: Modifier = Modifier
) {
    var expanded by remember {mutableStateOf(false)}
    Card(
        elevation = 10.dp,
        modifier = modifier
            .padding(8.dp)
            .background(color = MaterialTheme.colors.surface, shape = MaterialTheme.shapes.large)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
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
                    text = "Ocupacion: ${ocupacion.descripcion}",
                    modifier = Modifier.padding(5.dp)
                )
                Text(
                    text = "Email: ${persona.email}",
                    modifier = Modifier.padding(horizontal = 5.dp)
                )
            }
            ExpandButton(
                expanded = expanded,
                onClick = { expanded = !expanded}
            )
            if(expanded) {
                ExtraInformation(persona = persona)
            }
        }
    }
}

@Composable
private fun ExpandButton(
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
fun ExtraInformation (
    persona: Persona,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(
            horizontal = 5.dp, vertical = 3.dp
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Tel: ${persona.telefono}", modifier = Modifier.padding(horizontal = 5.dp))
            Text(text = "Cel: ${persona.celular}", modifier = Modifier.padding(horizontal = 5.dp))
        }
        Column (
            modifier = Modifier.fillMaxWidth()
        ){
            Text(text = "Nacimiento: ${DateConverter().aString(persona.fechaNacimiento)}", modifier = Modifier.padding(horizontal = 5.dp))
            Text(text ="Direccion: ${persona.direccion}", modifier = Modifier.padding(horizontal = 5.dp))
        }
    }
}

