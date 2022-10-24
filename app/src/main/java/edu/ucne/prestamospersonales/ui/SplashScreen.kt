package edu.ucne.prestamospersonales.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.ucne.prestamospersonales.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,){
            Image(
                painterResource(id = R.drawable.prestamosappforeground),
                contentDescription = null,
                modifier = Modifier.size(180.dp)
            )
            Text(
                text = "Prestamos Personales",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onBackground,
                fontSize = 30.sp,
                modifier = Modifier.padding(vertical = 20.dp)
            )
            CircularProgressIndicator(modifier = Modifier.size(120.dp))
        }
    }
}