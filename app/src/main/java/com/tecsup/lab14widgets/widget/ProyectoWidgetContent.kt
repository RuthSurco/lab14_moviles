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
 * Al tocarlo se abre la pantalla con el detalle de las entregas.
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
                    diasEntregas = entregas.map { it.dia }.toSet()
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
        diasEntregas: Set<Int>
    ) {
        val diasSemana = listOf("L", "M", "M", "J", "V", "S", "D")
        val tamanoCelda = 32.dp

        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .padding(12.dp)
                .background(GlanceTheme.colors.widgetBackground)
                .cornerRadius(20.dp)
                .clickable(actionStartActivity<DestinosActivity>()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Cabecera con color de marca
            Row(
                modifier = GlanceModifier
                    .fillMaxWidth()
                    .background(GlanceTheme.colors.primaryContainer)
                    .cornerRadius(16.dp)
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
                // Contador de entregas como acento visual
                Box(
                    modifier = GlanceModifier
                        .size(30.dp)
                        .background(GlanceTheme.colors.tertiary)
                        .cornerRadius(15.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = diasEntregas.size.toString(),
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = GlanceTheme.colors.onTertiary
                        )
                    )
                }
            }

            Spacer(modifier = GlanceModifier.height(10.dp))

            // Ancho fijo para que letras y números queden en la misma columna
            Column(modifier = GlanceModifier.width(tamanoCelda * 7)) {
                // Días de la semana
                Row {
                    diasSemana.forEach { dia ->
                        Box(
                            modifier = GlanceModifier.size(tamanoCelda),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = dia,
                                style = TextStyle(
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = GlanceTheme.colors.onSurfaceVariant
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = GlanceModifier.height(6.dp))

                // Cuadrícula del mes: 7 hijos por fila (Glance admite máximo 10)
                semanas.forEach { semana ->
                    Row {
                        semana.forEach { dia ->
                            Box(
                                modifier = GlanceModifier.size(tamanoCelda),
                                contentAlignment = Alignment.Center
                            ) {
                                if (dia == null) {
                                    // Celda vacía: no se muestra nada
                                } else {
                                    val esEntrega = dia in diasEntregas
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
                                            .size(28.dp)
                                            .background(fondo)
                                            .cornerRadius(14.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = dia.toString(),
                                            style = TextStyle(
                                                fontSize = 12.sp,
                                                fontWeight = if (esEntrega || esHoy)
                                                    FontWeight.Bold
                                                else
                                                    FontWeight.Normal,
                                                color = colorTexto
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
