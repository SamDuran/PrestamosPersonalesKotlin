package edu.ucne.prestamospersonales.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.FiberNew
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import edu.ucne.prestamospersonales.ui.theme.bgVariant3
import edu.ucne.prestamospersonales.ui.theme.bgVariant4

@Composable
fun RegistroBottomBar(
    onNuevoClick: () -> Unit,
    onEliminarClick: () -> Unit,
) {
    BottomAppBar(
        contentPadding = PaddingValues.Absolute(),
        modifier = Modifier.height(76.dp),
        cutoutShape = MaterialTheme.shapes.small.copy(
            CornerSize(percent = 50)
        )) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(brush = Brush.verticalGradient(
                    colors = listOf(
                        bgVariant3(),
                        bgVariant4()
                    )
                )),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FloatingActionButton(
                onClick = onNuevoClick,
                modifier = Modifier.size(135.dp)
                    .padding(start = 20.dp)
                    .border(BorderStroke(7.dp, MaterialTheme.colors.background), RoundedCornerShape(50.dp))
            ) {
                Icon(imageVector = Icons.Outlined.FiberNew,
                    contentDescription = "Nuevo",
                    modifier = Modifier
                        .size(45.dp)
                )
            }
            FloatingActionButton(
                onClick = onEliminarClick,
                modifier = Modifier.size(135.dp)
                    .padding(end = 20.dp)
                    .border(BorderStroke(7.dp, MaterialTheme.colors.background), RoundedCornerShape(50.dp))
            ) {
                Icon(imageVector = Icons.Outlined.Delete,
                    contentDescription = "Eliminar",
                    modifier = Modifier
                        .size(35.dp)
                )
            }
        }
    }
}