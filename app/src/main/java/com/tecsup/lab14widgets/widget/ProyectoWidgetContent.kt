package com.tecsup.lab14widgets.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.action.actionStartActivity
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.tecsup.lab14widgets.DestinosActivity
import com.tecsup.lab14widgets.MainActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private data class Accion(val titulo: String, val detalle: String)

/**
 * Widget del ejercicio final: combina tres buenas prácticas vistas en Android UI Kit:
 *  - Accesos directos a funciones importantes (botones).
 *  - Información en tiempo real (hora del último update).
 *  - Lista de últimas acciones.
 *
 * El contenido es "glanceable": jerarquía clara, texto corto, buen contraste y padding.
 */
class ProyectoWidgetContent : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val hora = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())

        val acciones = listOf(
            Accion("Último acceso", hora),
            Accion("Tareas pendientes", "3 por revisar"),
            Accion("Estado", "Sincronizado")
        )

        provideContent {
            GlanceTheme {
                ProyectoContent(hora = hora, acciones = acciones)
            }
        }
    }

    @Composable
    private fun ProyectoContent(hora: String, acciones: List<Accion>) {
        val colorTexto = GlanceTheme.colors.onBackground

        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .padding(16.dp)
                .background(GlanceTheme.colors.widgetBackground),
            verticalAlignment = Alignment.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Mi proyecto",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorTexto
                )
            )

            Text(
                text = "Actualizado $hora",
                style = TextStyle(fontSize = 12.sp, color = colorTexto),
                modifier = GlanceModifier.padding(bottom = 8.dp)
            )

            Row(horizontalAlignment = Alignment.CenterHorizontally) {
                Button(
                    text = "Inicio",
                    onClick = actionStartActivity<MainActivity>()
                )
                Spacer(modifier = GlanceModifier.width(8.dp))
                Button(
                    text = "Destinos",
                    onClick = actionStartActivity<DestinosActivity>()
                )
            }

            Text(
                text = "Últimas acciones",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorTexto
                ),
                modifier = GlanceModifier.padding(top = 12.dp, bottom = 4.dp)
            )

            acciones.forEach { accion ->
                Text(
                    text = "• ${accion.titulo}: ${accion.detalle}",
                    style = TextStyle(fontSize = 12.sp, color = colorTexto)
                )
            }
        }
    }
}
