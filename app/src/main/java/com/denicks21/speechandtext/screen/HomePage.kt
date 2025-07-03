package com.denicks21.speechandtext.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.denicks21.speechandtext.R

// -----------------------
// Constantes de configuración
// -----------------------

// Controlan niveles de transparencia en las tarjetas
private const val MAIN_CARD_ALPHA = 0.3f            // Opacidad de la tarjeta principal
private const val METRIC_CARD_ALPHA = 0.3f          // Opacidad de las tarjetas de métrica

// Radios de las esquinas para dar estilo redondeado
private val MAIN_CARD_CORNER = 16.dp                // Esquinas redondeadas de la tarjeta principal
private val METRIC_CARD_CORNER = 12.dp              // Esquinas redondeadas de las tarjetas de métrica

// Alturas fijas para mantener consistencia de diseño
private val MAIN_CARD_HEIGHT = 250.dp               // Altura de la tarjeta principal
private val METRIC_CARD_HEIGHT = 150.dp             // Altura de cada tarjeta de métrica

// Tamaños y espacios para el componente de ubicación
private val LOCATION_ICON_SIZE = 20.dp              // Tamaño del icono de “ubicación”
private val LOCATION_SPACING = 8.dp                 // Espacio horizontal entre icono y texto

// Función auxiliar que devuelve una ubicación de prueba
private fun getFakeLocation(): String = "Loja, Chaguarpamba, Ecuador"

@Composable
fun HomePage(
    speechInput:       String,        // Texto obtenido del reconocimiento de voz
    isListening:       Boolean,       // Indica si Kuntur está “escuchando” o no
    onToggleListening: () -> Unit     // Acción para alternar entre escuchar/apagar
) {
    // Contenedor principal con fondo degradado y padding externo
    Box(
        modifier = Modifier
            .fillMaxSize()  // ocupa toda la pantalla
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        MaterialTheme.colors.primary,        // inicio del gradiente
                        MaterialTheme.colors.primaryVariant  // fin del gradiente
                    )
                )
            )
            .padding(16.dp)   // margen interior alrededor del contenido
    ) {
        // Columna central que agrupa todos los elementos de la pantalla
        Column(
            modifier = Modifier
                .fillMaxWidth()                       // ancho completo
                .verticalScroll(rememberScrollState()), // permite scroll si hace falta
            verticalArrangement = Arrangement.Center,  // centra hijos en vertical
            horizontalAlignment = Alignment.CenterHorizontally  // centra hijos en horizontal
        ) {
            // — Ubicación con icono —
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp, top = 36.dp) // espaciado superior e inferior
            ) {
                Icon(
                    painter            = painterResource(id = R.drawable.ic_ubication), // icono de ubicación
                    contentDescription = "Ubicación",
                    tint               = MaterialTheme.colors.onPrimary,                // color del tema
                    modifier           = Modifier.size(LOCATION_ICON_SIZE)               // tamaño fijo
                )
                Spacer(modifier = Modifier.width(LOCATION_SPACING))  // espacio entre icono y texto
                Text(
                    text  = getFakeLocation(),                         // texto de ubicación
                    style = MaterialTheme.typography.subtitle2,        // estilo tipográfico
                    color = MaterialTheme.colors.onPrimary              // color de texto
                )
            }

            // — Tarjeta principal de estado de Kuntur —
            Card(
                modifier = Modifier
                    .fillMaxWidth()                         // ancho completo
                    .height(MAIN_CARD_HEIGHT)               // altura fija
                    .clip(RoundedCornerShape(MAIN_CARD_CORNER)), // bordes redondeados
                shape           = RoundedCornerShape(MAIN_CARD_CORNER),
                backgroundColor = MaterialTheme.colors.surface.copy(alpha = MAIN_CARD_ALPHA), // fondo semitransparente
                elevation       = 0.dp                  // sin sombra
            ) {
                Column(
                    modifier            = Modifier
                        .fillMaxSize()                  // ocupa todo el espacio de la tarjeta
                        .padding(24.dp),                // espacio interno
                    verticalArrangement = Arrangement.SpaceBetween, // separa icono, texto y botón de forma uniforme
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Icono dinámico on/off para Kuntur
                    Icon(
                        painter            = painterResource(
                            id = if (isListening) R.drawable.ic_kuntur_on else R.drawable.ic_kuntur_off
                        ),
                        contentDescription = null,
                        modifier           = Modifier.size(64.dp), // tamaño grande
                        tint               = Color.Unspecified     // mantiene colores originales del drawable
                    )

                    // Texto de estado con salto de línea y color condicional
                    val statusText = if (isListening) "Kuntur\na la escucha" else "Kuntur\napagado"
                    val statusColor = if (isListening) Color.Green else Color.Red
                    Text(
                        text      = statusText,
                        style     = MaterialTheme.typography.h6,
                        color     = statusColor,
                        modifier  = Modifier
                            .fillMaxWidth()                // ocupa ancho completo para centrar el texto
                            .padding(vertical = 8.dp),     // espacio arriba y abajo
                        textAlign = TextAlign.Center       // centra el texto horizontalmente
                    )

                    // Botón invisible que muestra solo la imagen on/off
                    IconButton(
                        onClick = onToggleListening,
                        modifier = Modifier
                            .size(128.dp)                // área clicable grande
                            .background(
                                color = Color.Transparent,  // sin fondo
                                shape = CircleShape          // clickeable circular
                            )
                    ) {
                        Icon(
                            painter            = painterResource(
                                id = if (isListening) R.drawable.ic_button_off else R.drawable.ic_button_on
                            ),
                            contentDescription = null,
                            modifier           = Modifier.fillMaxSize(), // ocupa toda el área
                            tint               = Color.Unspecified       // colores del drawable
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp)) // separador antes del texto transcrito

            // Texto transcrito en tiempo real
            Text(
                text     = if (speechInput.isNotBlank()) speechInput else "Aquí aparecerá tu texto...",
                style    = MaterialTheme.typography.body1,
                color    = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .fillMaxWidth() // ancho completo
                    .padding(0.dp)
            )
        }

        // — Métricas inferiores: dos tarjetas laterales —
        Row(
            modifier = Modifier
                .fillMaxWidth()                          // Ocupa todo el ancho disponible
                .align(Alignment.BottomCenter)           // Alinea esta fila al centro inferior de la pantalla
                .padding(bottom = 32.dp),                // Agrega espacio en la parte inferior
            horizontalArrangement = Arrangement.spacedBy(16.dp)  // Espacio entre las dos tarjetas
        ) {
        // Tarjeta de métrica izquierda (Última alerta)
        MetricCard(
            modifier   = Modifier
                .weight(1f)                            // Reparte el espacio equitativamente entre ambas tarjetas
                .height(METRIC_CARD_HEIGHT)            // Establece una altura fija definida previamente
                .clip(RoundedCornerShape(METRIC_CARD_CORNER)),  // Aplica esquinas redondeadas a la tarjeta
            icon       = painterResource(id = R.drawable.ic_notification),  // Ícono personalizado desde drawable
            label      = "Última alerta",                // Etiqueta descriptiva del contenido
            value      = "21:39",                        // Valor o dato mostrado en la tarjeta
            cardAlpha  = METRIC_CARD_ALPHA,              // Nivel de transparencia definido previamente
            cornerSize = METRIC_CARD_CORNER,             // Radio de las esquinas redondeadas
            elevation  = 0.dp                            // Sin sombra elevada
        )

        // Tarjeta de métrica derecha (Incidencias)
        MetricCard(
            modifier   = Modifier
                .weight(1f)                            // Reparte el espacio equitativamente entre ambas tarjetas
                .height(METRIC_CARD_HEIGHT)            // Establece una altura fija definida previamente
                .clip(RoundedCornerShape(METRIC_CARD_CORNER)),  // Aplica esquinas redondeadas a la tarjeta
            icon       = painterResource(id = R.drawable.ic_chart),  // Ícono personalizado desde drawable
            label      = "Incidencias",                  // Etiqueta descriptiva del contenido
            value      = "20",                           // Valor o dato mostrado en la tarjeta
            cardAlpha  = METRIC_CARD_ALPHA,              // Nivel de transparencia definido previamente
            cornerSize = METRIC_CARD_CORNER,             // Radio de las esquinas redondeadas
            elevation  = 0.dp                            // Sin sombra elevada
        )
    }
    }
}

@Composable
private fun MetricCard(
    modifier:    Modifier = Modifier,
    icon:        Painter,
    label:       String,
    value:       String,
    cardAlpha:   Float,
    cornerSize:  Dp,
    elevation:   Dp
) {
    Card(
        modifier        = modifier,
        shape           = RoundedCornerShape(cornerSize),
        backgroundColor = MaterialTheme.colors.surface.copy(alpha = cardAlpha),
        elevation       = elevation
    ) {
        Column(
            modifier            = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter      = icon,                  // Aquí usamos Painter ahora
                contentDescription = null,
                tint         = MaterialTheme.colors.onSurface
            )
            Text(
                text  = label,
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onSurface
            )
            Text(
                text  = value,
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onSurface
            )
        }
    }
}




