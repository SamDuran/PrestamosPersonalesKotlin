package edu.ucne.prestamospersonales

import android.app.PendingIntent.getActivity
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.Room
import edu.ucne.prestamospersonales.data.AppDB
import edu.ucne.prestamospersonales.models.Ocupacion
import edu.ucne.prestamospersonales.ui.theme.PrestamosPersonalesTheme
import java.security.AccessController.getContext

var changed = false
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val room = Room.databaseBuilder(this, AppDB::class.java, "ocupacion").build()

            var ocupacion : Ocupacion = Ocupacion(0,"", 0f)

            PrestamosPersonalesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    OcupacionScreen(room, ocupacion, this.getApplicationContext())
                    if(changed)
                        ocupacion = Ocupacion(0,"",0f)
                }
            }
        }
    }
}

@Composable
fun OcupacionScreen(
    database : AppDB,
    ocupacion : Ocupacion,
    context : Context
) {
    Scaffold(
        topBar = {
            TopAppBar(content = {Text(text = "Registro de ocupaciones")})
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                database.daoOcupacion.insertOcupacion(ocupacion)
                changed = true
                Toast.makeText(context,"",Toast.LENGTH_SHORT).show()
            }) {
                Icon(imageVector = Icons.Default.Create, contentDescription = "Guardar")
            }
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = {Text(text = "Descripción")},
                value = "",
                onValueChange = {ocupacion.descripcion = it}
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = {Text(text = "Salario")},
                value = "",
                onValueChange = {ocupacion.salario = customToFloat(it)}
            )
        }
    }
}


private fun validDescription(
    input : String
) {

}

private fun customToFloat(
    input : String
) : Float {
    var output = ""
    var havePunctuation = false
    for(number in input) {
        when {
            number.isDigit() -> output+=number
            number == '.' -> {
                if(havePunctuation) break
                havePunctuation = true
                output += number
            }
        }
    }
    return output.toFloat()
}

