# Laboratorio 14 — Widgets (Jetpack Glance)

Proyecto independiente del Laboratorio 14: creación de un widget de pantalla de inicio con Jetpack Glance.

## Objetivo

Aprender a crear un widget de pantalla de inicio en Android utilizando Jetpack Glance, implementar elementos básicos de interacción y personalizar su diseño.

## Progreso

| Ejercicio | Tema | Estado |
| --- | --- | --- |
| 1 | Configuración de Glance (dependencias, Manifest, receiver y contenido del widget) | ✅ |
| 8 (modificación) | Más de una vista y botón hacia otra vista | ✅ |
| Final | Widget personalizado del proyecto final | ✅ |

## Ejercicio 1 implementado

- Dependencias: `androidx.glance:glance-appwidget`, `glance-material3` y `glance-material` (versión 1.2.0).
- Configure `buildFeatures.compose = true` y el plugin `org.jetbrains.kotlin.plugin.compose` (Kotlin 2.2.10).
- Registro del receiver `SimpleWidget` en `AndroidManifest.xml`.
- Archivos `res/xml/simple_widget_info.xml` y `res/xml/simple_widget_layout.xml`.
- Clases `widget/SimpleWidget.kt` y `widget/SimpleWidgetContent.kt`.
- El widget muestra un `Text` y un `Button` que abre `MainActivity` con `actionStartActivity`.

> Nota: el laboratorio usa el nombre `@xml/my_app_widget_info` en el Manifest pero pide crear `simple_widget_info.xml`. En este proyecto se unificó el nombre como `@xml/simple_widget_info` para que compile.

## Parte 8 — Más de una vista

- Segunda pantalla: `DestinosActivity.kt` (lista de destinos + botón "Volver").
- El widget `SimpleWidget` ahora tiene dos botones:
  - **Página Principal** → `MainActivity`
  - **Ver destinos** → `DestinosActivity`
- `DestinosActivity` se registró en el `AndroidManifest.xml`.

## Ejercicio final — Widget personalizado

Widget `ProyectoWidget` basado en buenas prácticas de Android UI Kit / Glance:

- **Accesos directos**: botones "Inicio" y "Destinos".
- **Información en tiempo real**: hora del último update (`SimpleDateFormat`).
- **Lista de últimas acciones**: tres filas resumidas.
- Jerarquía visual clara, `GlanceTheme.colors.widgetBackground`, contraste con `onBackground`, padding de 16dp y tamaño mínimo 300dp × 180dp.
- Se actualiza cada 30 minutos (`updatePeriodMillis="1800000"`).

Archivos:
- `widget/ProyectoWidget.kt`
- `widget/ProyectoWidgetContent.kt`
- `res/xml/proyecto_widget_info.xml`

## Cómo ejecutar

1. Abrir esta carpeta (`lab14`) en Android Studio.
2. Sincronizar Gradle.
3. Ejecutar la app en un emulador/dispositivo (API 24 o superior).
4. En la pantalla de inicio, mantener presionado → **Widgets** → **Lab 14 Widgets**.
5. Hay dos widgets disponibles: el del Ejercicio 1 y el personalizado del ejercicio final. Arrastra cualquiera de ellos a la pantalla.

## Cómo verificar la compilación

```bash
./gradlew assembleDebug
```
