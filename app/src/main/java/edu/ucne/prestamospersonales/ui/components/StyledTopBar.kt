package edu.ucne.prestamospersonales.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import edu.ucne.prestamospersonales.ui.theme.bgBar1
import edu.ucne.prestamospersonales.ui.theme.bgBar2
import edu.ucne.prestamospersonales.ui.theme.bgCard1
import edu.ucne.prestamospersonales.ui.theme.bgCard2

enum class TopBarStyle {
    JustTitle,
    MenuTitleConfig,
    MenuTitleFind,
    BackTitleFind,
    BackTitle,
    MenuTitle
}

var search by mutableStateOf("")
var filter by mutableStateOf(0)

@Composable
fun StyledTopBar(
    style: TopBarStyle,
    showMenu: (() -> Unit)? = null,
    onBackClick: (() -> Unit)? = null,
    filtros: List<String>? = null,
    title: String = "Title",
) {
    var showSearch by remember { mutableStateOf(false) }
    var showFilters by remember { mutableStateOf(false) }

    val animateModifier = Modifier.animateContentSize(
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessVeryLow
        )
    )
    TopAppBar(
        contentPadding = PaddingValues.Absolute(),
        backgroundColor = Color.Transparent,
        modifier = if (showSearch) animateModifier.height(130.dp) else animateModifier.height(70.dp)
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            bgBar2(),
                            bgBar1()
                        )
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                when (style) {
                    TopBarStyle.JustTitle -> {
                        CompositionLocalProvider(
                            LocalTextStyle provides MaterialTheme.typography.h6
                        ) {
                            Text(
                                text = title,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                    }
                    TopBarStyle.MenuTitleConfig -> {

                        IconButton(onClick = showMenu!!) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Abrir menú",
                                tint = Color.White
                            )
                        }

                        CompositionLocalProvider(
                            LocalTextStyle provides MaterialTheme.typography.h6
                        ) {
                            Text(
                                text = title, color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            )
                        }

                        IconButton(onClick = { /*abrir configuraciones*/ }) {
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                contentDescription = "Ajustes",
                                tint = Color.White
                            )
                        }
                    }
                    TopBarStyle.MenuTitleFind -> {

                        IconButton(onClick = showMenu!!) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Abrir menú",
                                tint = Color.White
                            )
                        }

                        CompositionLocalProvider(
                            LocalTextStyle provides MaterialTheme.typography.h6
                        ) {
                            Text(
                                text = title, color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            )
                        }

                        IconButton(onClick = {
                            showSearch = !showSearch
                        }) {
                            Icon(
                                imageVector = if(showSearch)Icons.Filled.Close else Icons.Filled.Search,
                                contentDescription = "Buscar",
                                tint = Color.White
                            )
                        }
                    }
                    TopBarStyle.MenuTitle -> {

                        IconButton(onClick = showMenu!!) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Abrir menú",
                                tint = Color.White
                            )
                        }

                        CompositionLocalProvider(
                            LocalTextStyle provides MaterialTheme.typography.h6
                        ) {
                            Text(
                                text = title, color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            )
                        }
                    }
                    TopBarStyle.BackTitleFind -> {

                        IconButton(onClick = onBackClick!!) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Atras",
                                tint = Color.White
                            )
                        }


                        CompositionLocalProvider(
                            LocalTextStyle provides MaterialTheme.typography.h6
                        ) {
                            Text(
                                text = title, color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            )
                        }

                        IconButton(onClick = { showSearch = !showSearch }) {
                            Icon(
                                imageVector = if(showSearch)Icons.Filled.Close else Icons.Filled.Search,
                                contentDescription = "Busqueda",
                                tint = Color.White
                            )
                        }
                    }
                    TopBarStyle.BackTitle -> {

                        IconButton(onClick = onBackClick!!) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Atras",
                                tint = Color.White
                            )
                        }


                        CompositionLocalProvider(
                            LocalTextStyle provides MaterialTheme.typography.h6
                        ) {
                            Text(
                                text = title, color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            )
                        }
                    }
                }
            }
            if (showSearch) SearchField { showFilters = !showFilters } else {
                filter = 0
                search = ""
            }
            if (showFilters) FiltersMenu(
                filtros = filtros!!,
                onDimissRequest = { showFilters = false }) { indiceSeleccionado ->
                filter = indiceSeleccionado
            }
        }
    }
}

@Composable
private fun SearchField(
    showFilters: (() -> Unit)
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxWidth()
            .padding(bottom = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedTextField(
            colors = searchTextFieldColors(),
            modifier = Modifier.fillMaxWidth(0.8f),
            label = { Text("Busqueda") },
            value = search,
            onValueChange = { newValue ->
                search = newValue
            }
        )
        IconButton(
            onClick = showFilters,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.FilterList,
                tint = Color.White,
                contentDescription = "Filtros"
            )
        }
    }
}


@Composable
private fun FiltersMenu(
    filtros: List<String>,
    onDimissRequest: () -> Unit,
    onSelectFilter: (Int) -> Unit
) {
    Dialog(onDismissRequest = onDimissRequest) {
        LazyColumn(
            modifier = Modifier.background(
                brush = Brush.verticalGradient(
                    colors = listOf(bgCard1(), bgCard2())
                ), shape = MaterialTheme.shapes.medium
            ).alpha(1f)
        ) {
            item{
                Text(
                    text = "Filtros",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)
                )
            }
            item{
                Divider(modifier = Modifier.padding(8.dp))
            }
            for (i in filtros.indices) {
                this.item {
                    Text(
                        text = filtros[i],
                        textAlign = TextAlign.Center,
                        textDecoration = if (i == filter) TextDecoration.Underline else null,
                        fontWeight = if (i == filter) FontWeight.Bold else null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable(
                                onClick = {
                                    onSelectFilter(i)
                                    onDimissRequest()
                                }
                            )
                    )
                }
            }
        }
    }
}