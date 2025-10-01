# RubenApp — To-Do CRUD con Dashboard (Android/Kotlin)

App Android con **pantalla de bienvenida** y módulo de **tareas**: agregar, editar, eliminar (swipe con deshacer), compartir por Intent y **persistencia local** (SharedPreferences).

## ✨ Funcionalidades
- Dashboard: bienvenida + botón **Ver tareas**.
- CRUD de tareas:
  - **Agregar** (formulario), **Editar**, **Eliminar** (botón o swipe con Undo).
  - **Compartir** tarea: `Intent.ACTION_SEND`.
- UI bonita:
  - **Degradés** en pantallas.
  - **Tarjetas** con colores pastel.
- **Persistencia**: `TaskStore` (SharedPreferences + JSON).
- Ciclo de vida de Activities usado: `onCreate`, `onStop` (guardado seguro).

## 🧱 Estructura principal
- `HomeActivity` (dashboard)
- `MainActivity` (lista/CRUD)
- `DetailActivity` (formulario)
- `model/TaskStore.kt` (persistencia)
- `res/layout/*` (layouts), `res/drawable/*` (íconos/fondos), `res/values/*` (colores/tema)

## 🔧 Requisitos
- **minSdk 24**, **targetSdk 36**
- Android Studio (Jellyfish o superior)
- Kotlin + AndroidX + Material 3

## ▶️ Instalación / Ejecución
1. Abrir en Android Studio.
2. **Run ▶️** en emulador o dispositivo.
3. (Opcional) Generar APK:
   - Debug: `Build > Build APK(s)` → `app/build/outputs/apk/debug/app-debug.apk`
   - Release firmado: `Build > Generate Signed Bundle / APK…` → `app/build/outputs/apk/release/app-release.apk`

## 📦 APK
- Coloca aquí tu archivo: `apk/RubenApp-v1.apk`.
- **Descarga directa:** _(agrega el link al archivo en tu repo o en Releases)_

## 🖼️ Capturas
_(inserta imágenes en `screenshots/` y enlázalas)_

## 🧪 Cómo probar
- Abrir la app → **Ver tareas**.
- **+** para agregar, ✏️ para editar, 🗑️ para eliminar, swipe para borrar con **Deshacer**.
- Cierra y reabre: las tareas se mantienen (persistencia).

## 📚 Créditos
- Autor: _Tu nombre_
- Licencia: MIT (opcional)
