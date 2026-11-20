package com.tecsup.lab14widgets.widget

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver

/**
 * Receiver registrado en el AndroidManifest.xml.
 * Es el punto de entrada que el sistema usa para crear y actualizar el widget.
 */
class SimpleWidget : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = SimpleWidgetContent()
}
