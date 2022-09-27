package edu.ucne.prestamospersonales.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import edu.ucne.prestamospersonales.ui.theme.bgVariant3
import edu.ucne.prestamospersonales.ui.theme.bgVariant4

enum class TopBarStyles {
    JustTitle,
    MenuTitleConfig,
    MenuTitleFind,
    BackTitleFind,
    BackTitle,
    MenuTitle
}

var search by mutableStateOf("")

@Composable
fun StyledTopBar(
    style: TopBarStyles,
    showMenu: () -> Unit = {},
    onBackClick: () -> Unit = {},
    title: String = "Title",
) {
    var showSearch by remember { mutableStateOf(false) }

    val animateModifier = Modifier.animateContentSize(
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessHigh
        )
    )
    TopAppBar(
        contentPadding = PaddingValues.Absolute(),
        backgroundColor = Color.Transparent,
        modifier = if (showSearch) animateModifier.height(130.dp) else animateModifier.height(74.dp)
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            bgVariant4(),
                            bgVariant3()
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
                    TopBarStyles.JustTitle -> {
                        CompositionLocalProvider(
                            LocalContentAlpha provides ContentAlpha.high,
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
                    TopBarStyles.MenuTitleConfig -> {
                        CompositionLocalProvider(
                            LocalContentAlpha provides ContentAlpha.high
                        ) {
                            IconButton(onClick = showMenu) {
                                Icon(imageVector = Icons.Filled.Menu,
                                    contentDescription = "Abrir menú")
                            }
                        }

                        CompositionLocalProvider(
                            LocalContentAlpha provides ContentAlpha.high,
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

                        IconButton(onClick = { /*abrir configuraciones*/ }) {
                            Icon(imageVector = Icons.Filled.Settings,
                                contentDescription = "Ajustes")
                        }
                    }
                    TopBarStyles.MenuTitleFind -> {
                        CompositionLocalProvider(
                            LocalContentAlpha provides ContentAlpha.high
                        ) {
                            IconButton(onClick = showMenu) {
                                Icon(imageVector = Icons.Filled.Menu,
                                    contentDescription = "Abrir menú")
                            }
                        }

                        CompositionLocalProvider(
                            LocalContentAlpha provides ContentAlpha.high,
                            LocalTextStyle provides MaterialTheme.typography.h6
                        ) {
                            Text(
                                text = title,color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            )
                        }

                        IconButton(onClick = { showSearch = !showSearch }) {
                            Icon(imageVector = Icons.Filled.Search, contentDescription = "Buscar")
                        }
                    }
                    TopBarStyles.MenuTitle -> {
                        CompositionLocalProvider(
                            LocalContentAlpha provides ContentAlpha.medium
                        ) {
                            IconButton(onClick = showMenu) {
                                Icon(imageVector = Icons.Filled.Menu,
                                    contentDescription = "Abrir menú")
                            }
                        }

                        CompositionLocalProvider(
                            LocalContentAlpha provides ContentAlpha.medium,
                            LocalTextStyle provides MaterialTheme.typography.h5
                        ) {
                            Text(
                                text = title,color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                    }
                    TopBarStyles.BackTitleFind -> {
                        CompositionLocalProvider(
                            LocalContentAlpha provides ContentAlpha.high
                        ) {
                            IconButton(onClick = onBackClick) {
                                Icon(imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "Atras")
                            }
                        }

                        CompositionLocalProvider(
                            LocalContentAlpha provides ContentAlpha.high,
                            LocalTextStyle provides MaterialTheme.typography.h6
                        ) {
                            Text(
                                text = title,color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            )
                        }

                        IconButton(onClick = { showSearch = !showSearch }) {
                            Icon(imageVector = Icons.Filled.Search, contentDescription = "Busqueda")
                        }
                    }
                    TopBarStyles.BackTitle -> {
                        CompositionLocalProvider(
                            LocalContentAlpha provides ContentAlpha.high
                        ) {
                            IconButton(onClick = onBackClick) {
                                Icon(imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "Atras")
                            }
                        }

                        CompositionLocalProvider(
                            LocalContentAlpha provides ContentAlpha.medium,
                            LocalTextStyle provides MaterialTheme.typography.h6
                        ) {
                            Text(
                                text = title,color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            )
                        }
                    }
                }
            }
            if (showSearch) SearchField()
        }
    }
}

@Composable
fun SearchField(
    //pasarles filtros
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxWidth()
            .padding(bottom = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(0.8f),
            label = { Text("Busqueda") },
            value = search,
            onValueChange = { newValue ->
                search = newValue
            }
        )
        IconButton(
            onClick = {/*Desplegar filtos*/ },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Icon(imageVector = Icons.Outlined.FilterList,
                contentDescription = "Filtros")
        }
    }
}