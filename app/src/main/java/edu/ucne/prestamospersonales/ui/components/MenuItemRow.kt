package edu.ucne.prestamospersonales.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.ucne.prestamospersonales.ui.theme.PrestamosPersonalesTheme

data class MenuItem(
    val icon: ImageVector,
    val title: String,
    val onClick: () -> Unit,
)

@Composable
fun MenuItemRow(menuItem: MenuItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth().clip(shape = RoundedCornerShape(20.dp))
            .background(color = Color.Transparent)
            .padding(vertical = 7.dp)
            .clickable { menuItem.onClick() },

        ) {
        Icon(menuItem.icon,
            contentDescription = null,
            modifier = Modifier
                .size(30.dp)
                .padding(start = 15.dp))
        Text(menuItem.title,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(start = 40.dp))
    }
}

@Composable
fun SubMenuItem(
    icon: ImageVector? = null,
    title: String,
    onItemClick: () -> Unit = {},
    menuItems: List<MenuItem> = emptyList(),
) {
    var expanded by remember { mutableStateOf(false) }

    ItemCard(
        modifier = Modifier
            .padding(8.dp).padding(horizontal=10.dp)
            .fillMaxWidth()
            .clickable {
                if (menuItems.isNotEmpty()) {
                    expanded = !expanded
                } else {
                    onItemClick()
                }
            }

    ) {
        LazyColumn(modifier = Modifier.padding(5.dp)) {
            item {
                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .fillMaxWidth()) {
                    if (icon != null) {
                        Icon(
                            icon,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = 5.dp, end = 20.dp)
                                .padding(vertical = 10.dp)
                                .size(30.dp)
                        )
                    }
                    Text(
                        text = title,
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier
                            .padding(start = 10.dp, end = 50.dp)
                            .padding(vertical = 10.dp)
                    )
                    if (menuItems.isNotEmpty() && expanded) {
                        Icon(
                            imageVector = Icons.Filled.ArrowDropUp,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                    } else if (menuItems.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }//Titulo

            if (expanded) {
                item { Divider() }
                menuItems.forEach { menuItem ->
                    item {
                        MenuItemRow(menuItem = menuItem)
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun Preview() {
    PrestamosPersonalesTheme(darkTheme = true) {
        val listaPrueba = listOf(
            MenuItem(icon = Icons.Filled.Search, title = "Buscar", onClick = {}),
            MenuItem(icon = Icons.Filled.Save, title = "Guardar", onClick = {}),
            MenuItem(icon = Icons.Filled.Person, title = "Perfiles", onClick = {}),
            MenuItem(icon = Icons.Filled.RestoreFromTrash, title = "Remover", onClick = {}),
        )
        Surface {
            SubMenuItem(icon = Icons.Filled.Person, title = "Registros", menuItems = listaPrueba)
        }
    }
}