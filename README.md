# Laboratorio 14 — Widgets (Jetpack Glance)

Proyecto independiente del Laboratorio 14: creación de un widget de pantalla de inicio con Jetpack Glance.

## Objetivo

Aprender a crear un widget de pantalla de inicio en Android utilizando Jetpack Glance, implementar elementos básicos de interacción y personalizar su diseño.

## Progreso

| Ejercicio | Tema | Estado |
| --- | --- | --- |
| 1 | Configuración de Glance (dependencias, Manifest, receiver y contenido del widget) | ✅ |
| Modificación | Más de una vista y botón hacia otra vista | ⏳ |
| Final | Widget personalizado del proyecto final | ⏳ |

## Ejercicio 1 implementado

- Dependencias: `androidx.glance:glance-appwidget`, `glance-material3` y `glance-material` (versión 1.2.0).
- Configure `buildFeatures.compose = true` y el plugin `org.jetbrains.kotlin.plugin.compose` (Kotlin 2.2.10).
- Registro del receiver `SimpleWidget` en `AndroidManifest.xml`.
- Archivos `res/xml/simple_widget_info.xml` y `res/xml/simple_widget_layout.xml`.
- Clases `widget/SimpleWidget.kt` y `widget/SimpleWidgetContent.kt`.
- El widget muestra un `Text` y un `Button` que abre `MainActivity` con `actionStartActivity`.

> Nota: el laboratorio usa el nombre `@xml/my_app_widget_info` en el Manifest pero pide crear `simple_widget_info.xml`. En este proyecto se unificó el nombre como `@xml/simple_widget_info` para que compile.

## Cómo ejecutar

1. Abrir esta carpeta (`lab14`) en Android Studio.
2. Sincronizar Gradle.
3. Ejecutar la app en un emulador/dispositivo (API 24 o superior).
4. En la pantalla de inicio, mantener presionado → **Widgets** → **Lab 14 Widgets** → arrastrar el widget.

## Cómo verificar la compilación

```bash
./gradlew assembleDebug
```
