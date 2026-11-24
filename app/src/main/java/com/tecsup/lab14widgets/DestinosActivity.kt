package com.tecsup.lab14widgets

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.lab14widgets.ui.theme.Lab14Theme

/**
 * Segunda vista del laboratorio (parte 8), adaptada al proyecto:
 * muestra las notas sincronizadas desde Canvas.
 * El widget la abre con actionStartActivity<DestinosActivity>().
 */
class DestinosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab14Theme {
                Scaffold { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(24.dp)
                    ) {
                        Text(
                            text = "Notas (Canvas)",
                            style = MaterialTheme.typography.headlineMedium
                        )

                        Text(
                            text = "Promedio general: 16.0 / 20",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(top = 8.dp)
                        )

                        Text(
                            text = "Datos sincronizados desde Canvas",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 4.dp)
                        )

                        listOf(
                            "Programación · 17",
                            "Cálculo · 16",
                            "Física · 15",
                            "Base de datos · 16"
                        ).forEach { nota ->
                            Text(
                                text = "• $nota",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(top = 12.dp)
                            )
                        }

                        Button(
                            onClick = { finish() },
                            modifier = Modifier.padding(top = 24.dp)
                        ) {
                            Text("Volver")
                        }
                    }
                }
            }
        }
    }
}
