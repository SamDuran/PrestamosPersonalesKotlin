package edu.ucne.prestamospersonales.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import edu.ucne.prestamospersonales.ui.components.MenuItem
import edu.ucne.prestamospersonales.ui.components.StyledTopBar
import edu.ucne.prestamospersonales.ui.components.SubMenuItem
import edu.ucne.prestamospersonales.ui.components.TopBarStyle
import edu.ucne.prestamospersonales.ui.theme.bgBar1
import edu.ucne.prestamospersonales.ui.theme.bgBar2
import edu.ucne.prestamospersonales.ui.theme.bgVariant1
import edu.ucne.prestamospersonales.ui.theme.bgVariant2
import edu.ucne.prestamospersonales.util.Screen
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavHostController) {

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val subMenuRegitroItems = listOf(
        MenuItem(
            icon = Icons.Filled.Work,
            title = "Ocupacion",
            onClick = { navController.navigate(Screen.OcupacionScreen.ruta + "/0") }
        ),
        MenuItem(
            icon = Icons.Filled.Person,
            title = "Persona",
            onClick = { navController.navigate(Screen.PersonaScreen.ruta + "/0") }
        ),
        MenuItem(
            icon = Icons.Filled.Paid,
            title = "Prestamo",
            onClick = { navController.navigate(Screen.PrestamoScreen.ruta + "/0") }
        ),
        MenuItem(
            icon = Icons.Filled.Category,
            title = "Articulos",
            onClick = { navController.navigate(Screen.ArticulosScreen.ruta + "/0") }
        ),
    )
    val subMenuConsultaItems = listOf(
        MenuItem(
            icon = Icons.Filled.Work,
            title = "Ocupacion",
            onClick = { navController.navigate(Screen.OcupacionListScreen.ruta) }
        ),
        MenuItem(
            icon = Icons.Filled.Person,
            title = "Persona",
            onClick = { navController.navigate(Screen.PersonaListScreen.ruta) }
        ),
        MenuItem(
            icon = Icons.Filled.Paid,
            title = "Prestamo",
            onClick = { navController.navigate(Screen.PrestamosListScreen.ruta) }
        ),
        MenuItem(
            icon = Icons.Filled.Category,
            title = "Articulos",
            onClick = { navController.navigate(Screen.ArticulosListScreen.ruta) }
        ),
    )

    Scaffold(
        scaffoldState = scaffoldState,
        drawerShape = RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 20.dp,
            bottomEnd = 20.dp,
            bottomStart = 0.dp
        ),
        topBar = {
            StyledTopBar(
                style = TopBarStyle.MenuTitle,
                title = "Home",
                showMenu = {
                    scope.launch {
                        scaffoldState.drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                }
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.ArticulosListScreen.ruta) },modifier = Modifier.size(80.dp)
            ) {
                Icon(Icons.Outlined.Home, "Ir", modifier = Modifier.size(50.dp))
            }
        },
        isFloatingActionButtonDocked = true,
        bottomBar = {
            BottomAppBar(
                contentPadding = PaddingValues.Absolute(),
                backgroundColor = Color.Transparent,
                modifier = Modifier.height(76.dp),
                cutoutShape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(brush = Brush.verticalGradient(
                            colors = listOf(
                                bgBar1(),
                                bgBar2()
                            )
                        )),
                ){
                }
            }
        },
        drawerScrimColor = Color(0x9A000000),
        drawerContent = {
            Column(modifier = Modifier
                .fillMaxSize()
                .background(brush = Brush.verticalGradient(
                    colors = listOf(bgVariant1(), bgVariant2()))))
            {
                Text(
                    text = "Menú",
                    color = MaterialTheme.colors.onBackground,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                )
                Divider()
                Column(modifier = Modifier.fillMaxWidth()) {
                    SubMenuItem(title = "Registros",
                        icon = Icons.Filled.AddCircle,
                        menuItems = subMenuRegitroItems)
                    SubMenuItem(title = "Consultas",
                        icon = Icons.Filled.List,
                        menuItems = subMenuConsultaItems)
                }
            }
        }
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "Click en el Float Action Button \npara ir a la pantalla solicitada en la tarea",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}