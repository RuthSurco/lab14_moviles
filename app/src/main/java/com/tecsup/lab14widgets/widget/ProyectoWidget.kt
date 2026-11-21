package com.tecsup.lab14widgets.widget

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver

/**
 * Widget personalizado del ejercicio final.
 * Se registra en el AndroidManifest.xml con @xml/proyecto_widget_info.
 */
class ProyectoWidget : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = ProyectoWidgetContent()
}
