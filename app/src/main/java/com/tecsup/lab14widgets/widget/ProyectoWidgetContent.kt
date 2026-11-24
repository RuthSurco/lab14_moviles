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

private data class Nota(val curso: String, val calificacion: String)

/**
 * Widget del ejercicio final adaptado al proyecto: sistema de notas conectado a Canvas.
 * Muestra promedio, hora de actualización, accesos directos y últimas notas.
 */
class ProyectoWidgetContent : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val hora = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())

        val notas = listOf(
            Nota("Programación", "17"),
            Nota("Cálculo", "16"),
            Nota("Física", "15")
        )
        val promedio = "16.0"

        provideContent {
            GlanceTheme {
                ProyectoContent(hora = hora, promedio = promedio, notas = notas)
            }
        }
    }

    @Composable
    private fun ProyectoContent(hora: String, promedio: String, notas: List<Nota>) {
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
                text = "Mis notas",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorTexto
                )
            )

            Text(
                text = "Promedio: $promedio / 20",
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorTexto
                ),
                modifier = GlanceModifier.padding(top = 4.dp)
            )

            Text(
                text = "Canvas · Actualizado $hora",
                style = TextStyle(fontSize = 11.sp, color = colorTexto),
                modifier = GlanceModifier.padding(bottom = 8.dp)
            )

            Row(horizontalAlignment = Alignment.CenterHorizontally) {
                Button(
                    text = "Inicio",
                    onClick = actionStartActivity<MainActivity>()
                )
                Spacer(modifier = GlanceModifier.width(8.dp))
                Button(
                    text = "Ver notas",
                    onClick = actionStartActivity<DestinosActivity>()
                )
            }

            Text(
                text = "Últimas notas",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorTexto
                ),
                modifier = GlanceModifier.padding(top = 12.dp, bottom = 4.dp)
            )

            notas.forEach { nota ->
                Text(
                    text = "• ${nota.curso}: ${nota.calificacion}",
                    style = TextStyle(fontSize = 12.sp, color = colorTexto)
                )
            }
        }
    }
}
