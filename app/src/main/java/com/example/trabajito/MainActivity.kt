package com.example.trabajito

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                MenuLateralCompleto()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuLateralCompleto() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val opciones = listOf("Inicio", "Perfil", "Mensajes", "Favoritos")
    val iconos = listOf(Icons.Default.Home, Icons.Default.Person,
        Icons.Default.Email, Icons.Default.Star)
    var seleccion by remember { mutableStateOf(opciones[0]) }
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {
                Text("Estudiante UNA Puno", Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleMedium)
                HorizontalDivider()
                opciones.forEachIndexed { i, opcion ->
                    NavigationDrawerItem(
                        label = { Text(opcion) },
                        icon = { Icon(iconos[i], contentDescription = null) },
                        selected = opcion == seleccion,
                        onClick = {
                            seleccion = opcion
                            scope.launch { drawerState.close() }
                        },
                        badge = { if (opcion == "Mensajes") Text("12") },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(seleccion) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Abrir menú")
                        }
                    }
                )
            }
        ) { innerPadding ->
            Text("Pantalla: $seleccion", Modifier.padding(innerPadding).padding(16.dp))
        }
    }
}