# León — Monumentos, Restaurantes y Eventos

App Android en **Kotlin** que muestra información de la ciudad de León: monumentos, restaurantes y eventos. Incluye una lista para cada categoría, un mapa con todas las ubicaciones y sensores que adaptan la interfaz.

## Funcionalidades

- **Menú principal** con cuatro accesos: Monumentos, Restaurantes, Eventos y Mapa.
- **Listas** con `RecyclerView` para cada categoría (foto, nombre y detalle).
  - Al pulsar un **restaurante** se abre su web en un `WebView`.
  - Al pulsar un **monumento o evento** se muestra su información en un `Toast`.
- **Mapa** con **MapLibre** centrado en León y un marcador por cada ubicación.
- **Sensores**:
  - **Luz**: cambia automáticamente entre modo claro y oscuro.
  - **Acelerómetro**: si se agita el dispositivo, el fondo se pone rojo durante 1 segundo.

## Estructura

```
data/
├── Ubicacion.kt → Clase abstracta base
├── Monumento.kt → Monumento (estilo, época)
├── Restaurante.kt → Restaurante (tipo de cocina, web)
├── Evento.kt → Evento (fecha, tipo)
└── DatosLeon.kt → Datos de ejemplo de León

helpers/
├── UbicacionAdapter.kt → Adapter del RecyclerView
├── UbicacionViewHolder.kt → ViewHolder de cada fila
└── WebActivity.kt → WebView para las webs de restaurantes

ventanasBotones/
├── MonumentosActivity.kt → Lista de monumentos
├── RestaurantesActivity.kt→ Lista de restaurantes
├── EventosActivity.kt → Lista de eventos
└── MapActivity.kt → Mapa con todas las ubicaciones

MainActivity.kt → Menú principal + sensores
```

## Tecnologías

- **Lenguaje:** Kotlin
- **IDE:** Android Studio
- **UI:** Material Components, `CardView`, `RecyclerView`, `WebView`
- **Mapas:** MapLibre
- **Sensores:** `SensorManager` (luz y acelerómetro)
- **Navegación:** `Intent` entre Activities
