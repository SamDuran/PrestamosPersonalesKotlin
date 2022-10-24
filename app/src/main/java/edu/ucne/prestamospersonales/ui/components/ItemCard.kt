package edu.ucne.prestamospersonales.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import edu.ucne.prestamospersonales.ui.theme.bgCard1
import edu.ucne.prestamospersonales.ui.theme.bgCard2

@Composable
fun ItemCard(
    modifier: Modifier = Modifier,
    content : @Composable ColumnScope.() -> Unit
){
    Card(
        modifier = modifier.clip(RoundedCornerShape(10.dp)),
        backgroundColor = Color.Transparent,
        shape = RoundedCornerShape(10.dp),
        elevation = 15.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(brush = Brush.linearGradient(
                    colors = listOf(bgCard1(), bgCard2())
                ))
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                ),
            content = content
        )
    }
}