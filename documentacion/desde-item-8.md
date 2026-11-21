# Laboratorio 14 — Widgets (documentación desde el ítem 8)

> Contenido listo para copiar en el informe `.docx`. Corresponde a la parte 8 del Ejercicio 1, al Ejercicio Final, a la Reflexión y a la Pregunta de observación.

---

## 8. Modificación de la aplicación: más de una vista y botón hacia otra vista

### Objetivo

Cumplir con la instrucción 8 del laboratorio: *"Modifica tu aplicación con más de una vista, y añadele un botón al widget para que pueda iniciar la aplicación en alguna vista diferente al MainActivity"*.

### Descripción de lo implementado

Se añadió una segunda vista a la aplicación, `DestinosActivity`, y se modificó el widget del Ejercicio 1 para que tenga dos botones en un `Row`:

- **Página Principal**: abre `MainActivity` (comportamiento original).
- **Ver destinos**: abre `DestinosActivity`, una vista distinta a la principal.

`DestinosActivity` muestra una lista de destinos y un botón **Volver** que cierra la vista con `finish()`.

### Declaración de la segunda vista en el `AndroidManifest.xml`

```xml
<activity
    android:name=".DestinosActivity"
    android:exported="false"
    android:label="Destinos"
    android:theme="@style/Theme.Lab14Widgets" />
```

`exported="false"` es correcto porque la actividad solo se abre desde un `PendingIntent` creado por la propia aplicación; no necesita ser visible para otras apps.

### Botón agregado al widget (`SimpleWidgetContent.kt`)

```kotlin
Row(horizontalAlignment = Alignment.CenterHorizontally) {
    Button(
        text = "Página Principal",
        onClick = actionStartActivity<MainActivity>()
    )
    Spacer(modifier = GlanceModifier.width(8.dp))
    Button(
        text = "Ver destinos",
        onClick = actionStartActivity<DestinosActivity>()
    )
}
```

### Funcionamiento

1. El usuario arrastra el widget a la pantalla de inicio.
2. El widget muestra el texto y los dos botones.
3. Al tocar **Ver destinos**, Glance ejecuta la acción `actionStartActivity<DestinosActivity>()`, que abre la segunda vista.
4. Desde esa vista se puede volver con el botón **Volver**.

### Evidencia

- Captura del widget con los dos botones: `[insertar captura]`
- Captura de la vista `DestinosActivity`: `[insertar captura]`
- Video corto del funcionamiento: `[insertar nombre del video]`

---

## Ejercicio Final: widget personalizado

### Revisión de referencias

Se revisó la documentación de **Android UI Kit** en Figma referida a widgets y las guías oficiales de **Jetpack Glance**. De esas referencias se tomaron las siguientes buenas prácticas aplicadas al diseño:

- El contenido debe ser *glanceable*: se entiende de un vistazo, sin obligar al usuario a interactuar.
- Jerarquía visual clara: título, dato principal y detalles secundarios con tamaños de texto diferenciados.
- Información breve y accionable: textos cortos y botones con etiquetas evidentes.
- Espaciado y contraste adecuados: padding de 16 dp y uso de `GlanceTheme` para colores del tema.
- Actualización periódica cuando se muestra información variable.

### Widget creado

Se creó el widget **"Mi proyecto"**, que combina las tres alternativas sugeridas por el laboratorio:

| Alternativa del laboratorio | Cómo se implementó |
| --- | --- |
| Accesos directos a funciones importantes | Botones **Inicio** y **Destinos** |
| Información en tiempo real | Hora del último update (`Actualizado HH:mm`) |
| Lista de las últimas acciones | Tres filas: último acceso, tareas pendientes y estado |

### Estructura del widget

```
┌──────────────────────────────┐
│          Mi proyecto         │
│       Actualizado 14:32      │
│                              │
│   [Inicio]    [Destinos]     │
│                              │
│  Últimas acciones            │
│  • Último acceso: 14:32      │
│  • Tareas pendientes: 3      │
│  • Estado: Sincronizado      │
└──────────────────────────────┘
```

### Archivos creados

| Archivo | Función |
| --- | --- |
| `widget/ProyectoWidget.kt` | `GlanceAppWidgetReceiver` registrado en el Manifest |
| `widget/ProyectoWidgetContent.kt` | Contenido del widget con `provideGlance` |
| `res/xml/proyecto_widget_info.xml` | Metadatos: 300dp × 180dp y actualización cada 30 minutos |

### Código del receiver

```kotlin
class ProyectoWidget : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = ProyectoWidgetContent()
}
```

### Registro en el `AndroidManifest.xml`

```xml
<receiver
    android:name=".widget.ProyectoWidget"
    android:exported="true">
    <intent-filter>
        <action android:name="android.appwidget.action.APPWIDGET_UPDATE" />
    </intent-filter>
    <meta-data
        android:name="android.appwidget.provider"
        android:resource="@xml/proyecto_widget_info" />
</receiver>
```

### Metadatos del widget

```xml
<appwidget-provider xmlns:android="http://schemas.android.com/apk/res/android"
    android:minWidth="300dp"
    android:minHeight="180dp"
    android:updatePeriodMillis="1800000"
    android:initialLayout="@xml/simple_widget_layout"
    android:resizeMode="horizontal|vertical"
    android:widgetCategory="home_screen" />
```

`updatePeriodMillis="1800000"` equivale a 30 minutos, suficiente para mantener la hora mostrada sin consumir batería de forma excesiva.

### Obtención de la información en tiempo real

Dentro de `provideGlance` se obtiene la hora antes de.composear el contenido:

```kotlin
val hora = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())

provideContent {
    GlanceTheme {
        ProyectoContent(hora = hora, acciones = acciones)
    }
}
```

Se usa `SimpleDateFormat` en lugar de la API `java.time` para mantener compatibilidad con `minSdk = 24`.

### Decisiones de diseño

| Elemento | Decisión | Razón |
| --- | --- | --- |
| Contenedor | `Column` con `fillMaxSize()` y `padding(16.dp)` | Aprovecha el espacio y evita que el texto toque los bordes |
| Fondo | `GlanceTheme.colors.widgetBackground` | Respeta el tema claro/oscuro del sistema |
| Título | 18 sp y `FontWeight.Bold` | Establece la jerarquía visual |
| Hora | 12 sp | Dato secundario, no compite con el título |
| Botones | En un `Row` con `Spacer` de 8 dp | Accesos directos separados y fáciles de tocar |
| Últimas acciones | Texto de 12 sp precedido de viñeta | Lista breve y legible en un widget pequeño |

### Evidencia

- Captura del widget personalizado en la pantalla de inicio: `[insertar captura]`
- Captura del selector de widgets mostrando "Mi proyecto": `[insertar captura]`
- Video del funcionamiento: `[insertar nombre del video]`

---

## Reflexión

¿Qué beneficios encuentras en el uso de widgets? ¿Cómo podría mejorar la experiencia con tu aplicación el uso de widgets?

Los widgets acercan la funcionalidad de una aplicación al usuario sin exigirle que abra la app. En este laboratorio se comprobó que un widget permite ver información útil —como la hora del último acceso o el estado de las tareas— directamente en la pantalla de inicio, y además ofrece accesos directos a las vistas más usadas mediante un solo toque.

El principal beneficio es la **inmediatez**: el usuario no tiene que buscar el ícono de la app, esperar a que cargue y navegar hasta la pantalla que necesita. Un segundo beneficio es la **visibilidad**: la aplicación mantiene una presencia pequeña pero constante, lo que ayuda a que el usuario recuerde que existe. Un tercer beneficio es la **personalización**: el widget se puede adaptar al espacio disponible y mostrar solo lo importante.

En nuestra aplicación, el uso de widgets mejora la experiencia porque el widget del Ejercicio 1 funciona como un acceso rápido a la pantalla principal y a la vista de destinos, mientras que el widget final muestra un resumen del estado del proyecto. De esta forma, una acción que antes requería varios pasos ahora se resuelve con un toque. Como mejora adicional, se podría conectar el widget a datos reales de la aplicación (por ejemplo, una base de datos o un repositorio remoto) para que las "últimas acciones" y el estado no fueran datos de ejemplo, sino información sincronizada con la app.

---

## Pregunta de observación

### Selecciona una parte del laboratorio que te haya resultado más desafiante. Explica por qué fue difícil y qué hiciste para resolverlo.

La parte más desafiante fue comprender que **Glance no usa los mismos composables que Jetpack Compose** y que el widget **no se muestra dentro de la app**, sino que debe agregarse desde la pantalla de inicio. Al principio intentamos usar `Modifier`, `Text` y `Column` de `androidx.compose`, lo cual generaba confusión porque las firmas no coincidían con los ejemplos.

La dificultad aumentó por tres detalles del documento:

1. El texto de introducción decía "Jetpack Preview" cuando el framework correcto es **Jetpack Glance**.
2. El Manifest del laboratorio registraba `@xml/my_app_widget_info`, pero el archivo solicitado se llamaba `simple_widget_info.xml`; tal cual estaba, no compilaba.
3. La configuración proponía `kotlinCompilerExtensionVersion = "1.5"`, que corresponde a Kotlin 1.x, mientras el entorno actual usa Kotlin 2.2 con el plugin `org.jetbrains.kotlin.plugin.compose`.

Para resolverlo revisamos la documentación oficial de Glance, unificamos el nombre del recurso del widget como `@xml/simple_widget_info` y reemplazamos la configuración obsoleta del compilador por el plugin actual de Compose. Después compilamos con `./gradlew assembleDebug` después de cada cambio, hasta obtener `BUILD SUCCESSFUL`. Finalmente verificamos en el emulador que los dos widgets aparecieran en el selector y que sus botones abrieran las vistas correctas.

También fue necesario entender que `actionStartActivity<DestinosActivity>()` funciona aunque la actividad no sea el launcher: la acción se ejecuta mediante un `PendingIntent` creado por la propia aplicación, por lo que `android:exported="false"` es suficiente y más seguro.

### Explica cómo usaste la IA para el desarrollo de este laboratorio.

Usamos la IA como apoyo en cuatro momentos concretos:

1. **Revisión del laboratorio:** nos ayudó a localizar el enlace incrustado en "entorno de ejecución de Jetpack Compose" y a explicar que ese enlace lleva a las notas de versiones de Compose Runtime, no a la documentación de Glance.
2. **Detección de inconsistencias:** identificó el error de nombre del recurso XML, el enlace roto de `GlanceModifier` y el uso de una configuración de compilador obsoleta.
3. **Generación del código:** propuso la estructura del proyecto con las dependencias de Glance, el receiver, el contenido del widget, la segunda actividad y el widget final, adaptado a las versiones reales del entorno (AGP 9.3.2, Kotlin 2.2.10 y Glance 1.2.0).
4. **Verificación y documentación:** nos indicó cómo comprobar el resultado con `assembleDebug` y nos ayudó a redactar esta documentación.

La IA no reemplazó la verificación: cada archivo generado se compiló y se probó en el emulador. Cuando aparecían dudas sobre APIs de Glance (por ejemplo, los colores de `GlanceTheme` o los modificadores disponibles), contrastamos la respuesta con la documentación oficial antes de darla por válida.
