package com.example.tareafinalu2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tareafinalu2.ui.theme.TareaFinalU2Theme

class MainActivity : ComponentActivity() {

    /**
     * La aplicación se organiza mediante la estructura de la pantalla Scaffold.
     * La cual nos brinda, una disposición coherente y modular para la interfaz de usuario, permitiendo la inclusión de elementos como la barra superior, barra inferior de navegación, un botón de acción flotante y un área para mostrar contenido dinámico.
     *
     * Variables globales:
     *
     * - verFavContac: mutableStateOf que controla si se muestra la lista de contactos favoritos o la lista completa de contactos.
     * Se inicializa en false, lo que significa que, por defecto, se muestra la lista completa.
     * mutableStateOf permite gestionar el estado en Jetpack Compose, lo que garantiza que cualquier cambio en el valor de verFavContac actualice automáticamente la interfaz de usuario sin necesidad de manejar manualmente la recomposición.
     *
     * - contactos: mutableStateListOf que contiene la lista de contactos de tipo Contacto.
     * Esta lista es la fuente principal de datos para la aplicación y permite gestionar tanto contactos normales como favoritos.
     * mutableStateListOf garantiza que cualquier modificación en la lista, como añadir o eliminar contactos, desencadene automáticamente una actualización de la interfaz de usuario, manteniendo la vista sincronizada con el estado de los datos.
     *
     * - verFormulario: mutableStateOf que controla la visibilidad del formulario de añadir un nuevo contacto.
     * Se inicializa en false, lo que significa que el formulario está oculto por defecto.
     * Este estado mutable permite que la interfaz de usuario se actualice automáticamente cuando el usuario interactúa con el botón flotante, mostrando u ocultando el formulario según corresponda.
     *
     * Scaffold:
     *
     * - @param topBar: Barra superior de la pantalla que se construye con la función `CustomTopBar`.
     *   El título de la barra superior cambia dinámicamente en función de `verFavContac`, mostrando "Contactos" o "Favoritos".
     *
     * - @param bottomBar: Barra inferior que permite al usuario alternar entre la vista de contactos completos y la vista de favoritos.
     *   Llamamos a la función `CustomBottomBar`, que tiene botones para ajustar `verFavContac` y cambiar entre las dos listas.
     *
     * - @param floatingActionButton: Botón flotante que alterna la visibilidad del formulario de añadir contacto.
     *   Al hacer clic, cambia el valor de `verFormulario` gracias al bucle if, lo que controla si el formulario de nuevo contacto se muestra u oculta.
     *
     * - @param Content: Se encarga de decidir qué contenido mostrar en función de los estados `verFormulario` y `verFavContac`.
     *   En el caso de que `verFormulario` es `true`, se muestra el formulario para añadir un nuevo contacto.
     *   Si `verFavContac` es `true`, muestra solo los contactos favoritos; de lo contrario, muestra todos los contactos.
     *
     *      - innerPadding: Padding aplicado a la columna para evitar superposiciones con la interfaz.
     *      - verFormulario: Indicmos si se debe mostrar el formulario para añadir un nuevo contacto.
     *      - contactos: Lista de contactos que se muestra, filtrada según el estado de favoritos.
     *      - añadirContacto: Lambda que añade un nuevo contacto a la lista.
     *      - añadirFavorito: Lambda que alterna el estado de favorito de un contacto.
     *
     * Clases:
     *
     * - Contacto: Esta clase contiene tres propiedades: `nombre` (String) que almacena el nombre del contacto, `telefono` (String) que guarda el número de teléfono del contacto,
     *   y `favorito` (Boolean), que indica si el contacto es un favorito, inicializándose en false por defecto.
     *   Gracias a esta clase gestionamos la información de los contactos de manera estructurada y sencilla.
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TareaFinalU2Theme {
                App()
            }
        }
    }
}

data class Contacto(val nombre: String, val telefono: String, var favorito: Boolean = false)

@Composable
fun App() {
    var verFormulario by remember { mutableStateOf(false) }
    var verFavContac by remember { mutableStateOf(false) }
    var contactos = remember { mutableStateListOf<Contacto>() }

    Scaffold(
        topBar = {
            CustomTopBar(
                if (verFavContac)
                    "Favoritos"
                else
                    "Contactos"
            )
        },
        bottomBar = {
            CustomBottomBar(
                onContactosClick = { verFavContac = false },
                onFavoritosClick = { verFavContac = true }
            )
        },
        floatingActionButton = {
            CustomFloatingActionButton {
                if (!verFormulario)
                    verFormulario = true
                else
                    verFormulario = false
            }
        }
    ) { padding ->
        Content(
            innerPadding = padding,
            verFormulario = verFormulario,
            contactos = if (verFavContac) contactos.filter { it.favorito } else contactos,
            añadirContacto = { contacto -> contactos.add(contacto) },
            añadirFavorito = { contacto ->
                val index = contactos.indexOf(contacto)
                contactos[index] = contacto.copy(favorito = !contacto.favorito)
            }
        )
    }
}

/**
 * - @composable fun CustomTopBar(): Composable en el que representa la barra superior de la aplicación, utilizando `TopAppBar` para mostrar un título en la parte superior de la pantalla.
 *   El parámetro `title` es un String que define el texto que se visualizará en la barra superior, proporcionando contexto sobre la vista actual.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(title: String) {
    TopAppBar(
        title = {
            Text(text = title)
        }
    )
}

/**
 * - @composable fun CustomBottomBar(): Composable en el que representa la barra inferior de la aplicación, utilizando `BottomAppBar`.
 *   En esta función recibimos dos lambdas como parámetros: `onContactosClick`, que se ejecuta cuando se hace clic en el botón de contactos, y `onFavoritosClick`,
 *   que se ejecuta al seleccionar el botón de favoritos. La barra inferior organiza sus elementos en una fila `Row` que hace que se distribuyan los
 *   botones, cada uno con un icono y un texto correspondientes que indica la función (Contactos o Favoritos), mejorando así la usabilidad.
 */
@Composable
fun CustomBottomBar(onContactosClick: () -> Unit, onFavoritosClick: () -> Unit) {
    BottomAppBar {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                IconButton(
                    onClick = onContactosClick
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Contactos",
                        modifier = Modifier.size(30.dp)
                    )
                }
                Text(text = "Contactos")
            }
            Spacer(modifier = Modifier.width(15.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = onFavoritosClick) {
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = "Favoritos",
                        modifier = Modifier.size(30.dp)
                    )
                }
                Text(text = "Favoritos")
            }
        }
    }
}

/**
 * - @composable fun CustomFloatingActionButton(): Composable en el que se representa un botón de acción flotante en la aplicación, utilizando `FloatingActionButton`.
 *   Esta función recibe un parámetro lambda `onMasClick`, que se ejecuta al hacer clic en el botón.
 *   El botón muestra un ícono de adición `Icon(Icons.Default.Add)`, en el que se utiliza para añadir elementos.
 */
@Composable
fun CustomFloatingActionButton(onMasClick: () -> Unit) {
    FloatingActionButton(onClick = onMasClick) {
        Icon(Icons.Default.Add, contentDescription = "Add")
    }
}

/**
 * - @composable fun ListaContactos(): Composable en el que se  muestra una lista de contactos en un formato desplazable porque se utiliza `LazyColumn`.
 *   Esta función recibe dos parámetros: `contactos`, que es una lista de objetos de tipo `Contacto` a mostrar,
 *   y `onToggleFavorito`, una lambda que se ejecuta para alternar el estado de favorito de un contacto al interactuar con él.
 *   Cada contacto se presenta a través de la función `CartaContactos`.
 */
@Composable
fun ListaContactos(contactos: List<Contacto>, onToggleFavorito: (Contacto) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(contactos) { contacto ->
            CartaContactos(contacto = contacto, añadirFavorito = onToggleFavorito)
        }
    }
}

/**
 * - @composable fun CartaContactos(): Composable que representa una tarjeta individual para mostrar la información de un contacto, utilizando `Card`.
 *   La función recibe dos parámetros: `contacto`, un objeto de tipo `Contacto` que contiene los detalles del contacto a mostrar, y `añadirFavorito`, una lambda que se ejecuta al alternar el estado de favorito del contacto.
 *   Dentro de la tarjeta, se organiza la información en una fila `Row` que incluye una imagen representativa del contacto, su nombre y teléfono, así como un interruptor `Switch` para poder marcarlo como favorito.
 */
@Composable
fun CartaContactos(contacto: Contacto, añadirFavorito: (Contacto) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Contacto",
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Column {
                Text(text = contacto.nombre)
                Text(text = contacto.telefono)
            }
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = contacto.favorito,
                onCheckedChange = { añadirFavorito(contacto) },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                    uncheckedThumbColor = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

/**
 * - @composable fun formulario(): Composable que presenta un formulario para añadir un nuevo contacto,en el cual recogemos el nombre y el teléfono del contacto mediante dos campos de texto `TextField`.
 *   Utiliza `mutableStateOf` para gestionar el estado de los campos, asegurando que cualquier cambio en el texto se refleje en la interfaz de usuario.
 *   La disposición se organiza en una columna `Column` con un espaciado uniforme `padding`.
 *   Cada `TextField` está acompañado de una etiqueta `label` que indica al usuario el tipo de información que debe ingresar, y al hacer clic en el botón (Button`, se valida que ambos campos no estén vacíos.
 *   Si los campos son válidos, se invoca la lambda `añadirContacto`, creando un nuevo objeto `Contacto` con los datos ingresados y reiniciando los campos, listos para una nueva entrada.
 */
@Composable
fun formulario(añadirContacto: (Contacto) -> Unit) {
    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") }
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = telefono,
            onValueChange = { telefono = it },
            label = { Text("Teléfono") }
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            if (nombre.isNotBlank() && telefono.isNotBlank()) {
                añadirContacto(Contacto(nombre, telefono))
                nombre = ""
                telefono = ""
            }
        }) {
            Text(text = "Añadir Contacto")
        }
    }
}

/**
 * - @composable fun Content(): Composable que representa el contenido principal de la pantalla, gestionando la disposición de elementos visuales en una columna `Column`.
 *   Recibe parámetros como `innerPadding`, que ajusta el espaciado interno, y un booleano `verFormulario` que determina si se debe mostrar el formulario para añadir un nuevo contacto.
 *   Cuando `verFormulario` es verdadero, se invoca la función `formulario`, permitiendo al usuario ingresar los detalles de un contacto.
 *   Además, muestra la lista de contactos utilizando `ListaContactos`, pasando la lista de contactos existente y la lambda `añadirFavorito` para permitir al usuario marcar contactos como favoritos.
 *   La columna está configurada para ocupar  y aplicar el espaciado interior recibido, proporcionando una experiencia de usuario organizada y clara.
 */
@Composable
fun Content(
    innerPadding: PaddingValues,
    verFormulario: Boolean,
    contactos: List<Contacto>,
    añadirContacto: (Contacto) -> Unit,
    añadirFavorito: (Contacto) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        if (verFormulario) {
            formulario(añadirContacto)
        }
        ListaContactos(contactos, añadirFavorito)
    }
}

/**
 * - @composable fun GreetingPreview(): Composable de vista previa que permite visualizar el diseño de la aplicación.
 */
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TareaFinalU2Theme {
        App()
    }
}