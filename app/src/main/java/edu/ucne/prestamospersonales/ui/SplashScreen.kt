package edu.ucne.prestamospersonales.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navigate: () -> Unit) {
    LaunchedEffect(key1 = true) {
        delay(2500)
        navigate()
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
    ) {
        Column{
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally).size(180.dp))
            Text(text = "Cargando...",
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h4,
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
            )
        }
    }
}