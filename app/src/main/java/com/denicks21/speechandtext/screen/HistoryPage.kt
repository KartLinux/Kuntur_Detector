package com.denicks21.speechandtext.screen


import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import java.io.File

@Composable
fun HistoryPage(navController: NavHostController) {
    val context = LocalContext.current
    var files by remember { mutableStateOf(emptyList<File>()) }

    // Lee los .txt de /files/SpeechAndText
    LaunchedEffect(Unit) {
        val dir = File(context.getExternalFilesDir("SpeechAndText"), "")
        if (dir.exists()) {
            files = dir.listFiles { f -> f.extension == "txt" }?.toList() ?: emptyList()
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(files) { file ->
                Text(
                    text = file.name,
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            // aquí podrías abrir / reproducir la grabación o mostrar detalle
                        }
                        .padding(vertical = 8.dp)
                )
            }
            if (files.isEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text("No hay grabaciones aún.", style = MaterialTheme.typography.body2)
                }
            }
        }
    }
}
