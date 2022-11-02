package edu.ucne.prestamospersonales.ui.articulos

import android.annotation.SuppressLint
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
import edu.ucne.prestamospersonales.data.remote.dto.ArticuloDto
import edu.ucne.prestamospersonales.ui.components.ItemCard
import edu.ucne.prestamospersonales.ui.components.StyledTopBar
import edu.ucne.prestamospersonales.ui.components.TopBarStyle

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ArticulosListScreen(
    onBackClick: () -> Unit,
    AddClick : () -> Unit,
    viewModel: ArticulosListViewModel = hiltViewModel(),
    onItemClick: (Int) -> Unit
) {

    viewModel.getList()

    Scaffold(
        topBar = {
            StyledTopBar(
                style = TopBarStyle.BackTitleFind,
                title = "Articulos",
                onBackClick = onBackClick,
                filtros = listOf("ID","Descripción","Marca","Existencia")
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = AddClick) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar articulo")
            }
        }
    ) {
        val uiState by viewModel.uiState.collectAsState()
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            ArticulosList(
                articulos = uiState.articulos,
                modifier = Modifier.fillMaxSize(),
                onItemClick = onItemClick
            )
        }

    }

}

@Composable
fun ArticulosList(
    articulos: List<ArticuloDto>,
    modifier: Modifier,
    onItemClick: (Int) -> Unit
) {
    LazyColumn(modifier = modifier.background(MaterialTheme.colors.background)) {
        this.items(articulos) { articulo ->
            ArticuloRow(articulo)
            { articuloId ->
                onItemClick(articuloId)
            }
        }
    }
}

@Composable
fun ArticuloRow(
    articulo: ArticuloDto,
    onItemClick: (Int) -> Unit
) {
    ItemCard(modifier = Modifier.padding(8.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .clickable { onItemClick(articulo.articuloId) }
        ) {
            Text(
                text = articulo.descripcion,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(horizontal = 5.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Marca: ${articulo.marca}",
                    modifier = Modifier.padding(horizontal = 5.dp)
                )
                Text(
                    text = "Qt: ${articulo.existencia}",
                    modifier = Modifier.padding(horizontal = 5.dp)
                )
            }
        }
    }
}
