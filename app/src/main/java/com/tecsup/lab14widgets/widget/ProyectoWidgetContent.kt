package com.tecsup.lab14widgets.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private data class Entrega(val dia: Int, val titulo: String)

/**
 * Widget del ejercicio final: calendario de entregas del proyecto.
 * Muestra el mes actual, resalta hoy y marca las fechas importantes con color.
 */
class ProyectoWidgetContent : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val calendario = Calendar.getInstance()
        val anio = calendario.get(Calendar.YEAR)
        val mes = calendario.get(Calendar.MONTH)
        val hoy = calendario.get(Calendar.DAY_OF_MONTH)
        val nombreMes = SimpleDateFormat("MMMM", Locale.getDefault())
            .format(calendario.time)
            .replaceFirstChar { it.uppercase() }

        val primerDia = Calendar.getInstance().apply { set(anio, mes, 1) }
            .get(Calendar.DAY_OF_WEEK)
        val offset = (primerDia + 5) % 7 // Lunes = 0
        val diasEnMes = calendario.getActualMaximum(Calendar.DAY_OF_MONTH)

        val celdas = mutableListOf<Int?>().apply {
            repeat(offset) { add(null) }
            addAll((1..diasEnMes).toList())
            while (size % 7 != 0) add(null)
        }
        val semanas = celdas.chunked(7)

        // Fechas de ejemplo: cámbialas por las entregas reales de tu proyecto.
        val entregas = listOf(
            Entrega(25, "Proyecto final"),
            Entrega(30, "Laboratorio 14")
        )

        provideContent {
            GlanceTheme {
                CalendarioContent(
                    nombreMes = nombreMes,
                    anio = anio,
                    hoy = hoy,
                    semanas = semanas,
                    entregas = entregas
                )
            }
        }
    }

    @Composable
    private fun CalendarioContent(
        nombreMes: String,
        anio: Int,
        hoy: Int,
        semanas: List<List<Int?>>,
        entregas: List<Entrega>
    ) {
        val diasSemana = listOf("L", "M", "M", "J", "V", "S", "D")

        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .padding(12.dp)
                .background(GlanceTheme.colors.widgetBackground)
                .cornerRadius(18.dp)
                .clickable(actionStartActivity<DestinosActivity>()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Cabecera
            Row(
                modifier = GlanceModifier
                    .fillMaxWidth()
                    .background(GlanceTheme.colors.primaryContainer)
                    .cornerRadius(14.dp)
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = GlanceModifier.defaultWeight()) {
                    Text(
                        text = "Calendario",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = GlanceTheme.colors.onPrimaryContainer
                        )
                    )
                    Text(
                        text = "$nombreMes $anio",
                        style = TextStyle(
                            fontSize = 12.sp,
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

            Spacer(modifier = GlanceModifier.height(8.dp))

            // Días de la semana
            Row(horizontalAlignment = Alignment.CenterHorizontally) {
                diasSemana.forEach { dia ->
                    Text(
                        text = dia,
                        modifier = GlanceModifier.defaultWeight(),
                        style = TextStyle(
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = GlanceTheme.colors.onSurfaceVariant
                        )
                    )
                }
            }

            Spacer(modifier = GlanceModifier.height(4.dp))

            // Cuadrícula del mes
            semanas.forEach { semana ->
                Row(horizontalAlignment = Alignment.CenterHorizontally) {
                    semana.forEach { dia ->
                        if (dia == null) {
                            Spacer(modifier = GlanceModifier.size(30.dp))
                        } else {
                            val esEntrega = entregas.any { it.dia == dia }
                            val esHoy = dia == hoy
                            val fondo = when {
                                esEntrega -> GlanceTheme.colors.tertiaryContainer
                                esHoy -> GlanceTheme.colors.primary
                                else -> GlanceTheme.colors.surfaceVariant
                            }
                            val colorTexto = when {
                                esEntrega -> GlanceTheme.colors.onTertiaryContainer
                                esHoy -> GlanceTheme.colors.onPrimary
                                else -> GlanceTheme.colors.onSurfaceVariant
                            }
                            Box(
                                modifier = GlanceModifier
                                    .size(30.dp)
                                    .background(fondo)
                                    .cornerRadius(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = dia.toString(),
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        fontWeight = if (esEntrega || esHoy) FontWeight.Bold else FontWeight.Normal,
                                        color = colorTexto
                                    )
                                )
                            }
                            Spacer(modifier = GlanceModifier.width(2.dp))
                        }
                    }
                }
                Spacer(modifier = GlanceModifier.height(2.dp))
            }
        }
    }
}
