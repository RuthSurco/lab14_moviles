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
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.width
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.tecsup.lab14widgets.DestinosActivity
import com.tecsup.lab14widgets.MainActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private data class Nota(val curso: String, val calificacion: Int)

/**
 * Widget del ejercicio final adaptado al proyecto: sistema de notas conectado a Canvas.
 * Diseño con identidad visual: cabecera de color, tarjeta de promedio y notas con acentos.
 */
class ProyectoWidgetContent : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val hora = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())

        val notas = listOf(
            Nota("Programación", 17),
            Nota("Cálculo", 16),
            Nota("Física", 15)
        )
        val promedio = notas.map { it.calificacion }.average()
        val promedioTexto = String.format(Locale.getDefault(), "%.1f", promedio)

        provideContent {
            GlanceTheme {
                ProyectoContent(hora = hora, promedio = promedioTexto, notas = notas)
            }
        }
    }

    @Composable
    private fun ProyectoContent(hora: String, promedio: String, notas: List<Nota>) {
        val colorTexto = GlanceTheme.colors.onBackground

        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .padding(12.dp)
                .background(GlanceTheme.colors.widgetBackground)
                .cornerRadius(16.dp),
            verticalAlignment = Alignment.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Cabecera con color de marca
            Row(
                modifier = GlanceModifier
                    .fillMaxWidth()
                    .background(GlanceTheme.colors.primaryContainer)
                    .cornerRadius(12.dp)
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = GlanceModifier.defaultWeight()) {
                    Text(
                        text = "Mis notas",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = GlanceTheme.colors.onPrimaryContainer
                        )
                    )
                    Text(
                        text = "Canvas · Actualizado $hora",
                        style = TextStyle(
                            fontSize = 11.sp,
                            color = GlanceTheme.colors.onPrimaryContainer
                        )
                    )
                }
                Box(
                    modifier = GlanceModifier
                        .size(12.dp)
                        .background(GlanceTheme.colors.tertiary)
                        .cornerRadius(6.dp)
                ) {}
            }

            Spacer(modifier = GlanceModifier.height(10.dp))

            // Tarjeta del promedio
            Column(
                modifier = GlanceModifier
                    .fillMaxWidth()
                    .background(GlanceTheme.colors.secondaryContainer)
                    .cornerRadius(12.dp)
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "PROMEDIO GENERAL",
                    style = TextStyle(
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = GlanceTheme.colors.onSecondaryContainer
                    )
                )
                Text(
                    text = "$promedio / 20",
                    style = TextStyle(
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = GlanceTheme.colors.onSecondaryContainer
                    )
                )
            }

            Spacer(modifier = GlanceModifier.height(10.dp))

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

            Spacer(modifier = GlanceModifier.height(10.dp))

            Text(
                text = "Últimas notas",
                style = TextStyle(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorTexto
                ),
                modifier = GlanceModifier.padding(bottom = 6.dp)
            )

            notas.forEach { nota ->
                Row(
                    modifier = GlanceModifier
                        .fillMaxWidth()
                        .background(GlanceTheme.colors.surfaceVariant)
                        .cornerRadius(8.dp)
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = nota.curso,
                        modifier = GlanceModifier.defaultWeight(),
                        style = TextStyle(fontSize = 12.sp, color = GlanceTheme.colors.onSurfaceVariant)
                    )
                    Text(
                        text = nota.calificacion.toString(),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (nota.calificacion >= 16)
                                GlanceTheme.colors.primary
                            else
                                GlanceTheme.colors.tertiary
                        )
                    )
                }
                Spacer(modifier = GlanceModifier.height(4.dp))
            }
        }
    }
}
