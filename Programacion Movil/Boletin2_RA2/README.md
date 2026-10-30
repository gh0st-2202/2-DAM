# Calculadora de IMC

App Android en **Kotlin** que calcula el **Índice de Masa Corporal (IMC)** a partir de la altura y el peso, y muestra la categoría resultante con colores.

## Funcionalidades

- Selección de **sexo** (hombre / mujer) con `MaterialButtonToggleGroup`.
- Ajuste de **altura** y **peso** con dos `Slider`.
- Cálculo del **IMC** en tiempo real (`peso / altura²`).
- Clasificación del resultado con color según la categoría:
  - **Bajo peso** (< 18.5)
  - **Peso normal** (18.5 – 24.9)
  - **Sobrepeso** (25 – 29.9)
  - **Obesidad** (≥ 30)
- Los textos se actualizan automáticamente al mover los sliders o cambiar el sexo.

## Tecnologías

- **Lenguaje:** Kotlin
- **IDE:** Android Studio
- **UI:** Material Components (`Slider`, `MaterialCardView`, `MaterialButtonToggleGroup`)
- **Recursos:** textos y colores definidos en `strings.xml` y `colors.xml`

## Estructura

```
MainActivity.kt
 ├── iniciarComponentes()   → Enlaza las vistas del layout
 ├── configurarListeners()  → Sliders y toggle group
 └── actualizarTodo()       → Calcula el IMC y actualiza la UI
```
