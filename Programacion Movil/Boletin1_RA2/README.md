# Ciudad León

App Android en **Kotlin** que muestra información de la ciudad de León organizada en dos secciones: **Monumentos** y **Negocios**.

## Funcionalidades

- Pantalla principal con dos accesos: **Monumentos** y **Negocios**.
- Navegación a `MonumentosActivity` y `NegociosActivity` al pulsar cada sección.
- Botón de prueba (`btn_javi`) que muestra un `Toast`.

## Estructura

```
MainActivity.kt          → Menú principal
MonumentosActivity.kt    → Sección de monumentos
NegociosActivity.kt      → Sección de negocios
```

## Tecnologías

- **Lenguaje:** Kotlin
- **IDE:** Android Studio
- **Interfaz:** XML con `AppCompatActivity`
- **Navegación:** `Intent` entre Activities
- **UI:** `enableEdgeToEdge` y `WindowInsets` para ajustar los bordes
