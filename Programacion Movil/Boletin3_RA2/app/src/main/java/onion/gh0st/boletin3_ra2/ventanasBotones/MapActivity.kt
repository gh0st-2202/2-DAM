package onion.gh0st.boletin3_ra2.ventanasBotones

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import onion.gh0st.boletin3_ra2.R
import onion.gh0st.boletin3_ra2.data.DatosLeon
import org.maplibre.android.MapLibre
import org.maplibre.android.annotations.MarkerOptions
import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.maps.MapView

class MapActivity : AppCompatActivity() {

    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapLibre.getInstance(this)

        enableEdgeToEdge()
        setContentView(R.layout.activity_map)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initMap(savedInstanceState)
    }

    /**
     * Funcion encargada de inicializar el mapa
     */
    private fun initMap(savedInstanceState: Bundle?) {
        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)

        mapView.getMapAsync { map ->
            map.setStyle("https://tiles.openfreemap.org/styles/liberty") {

                val centroLeon = LatLng(42.5985, -5.5700)
                map.cameraPosition = CameraPosition.Builder()
                    .target(centroLeon)
                    .zoom(15.2)
                    .build()

                // Fragmento de codigo encargado de cargar todas las ubicaciones en el mapa
                DatosLeon.todas(this).forEach { ubicacion ->
                    map.addMarker(
                        MarkerOptions()
                            .position(LatLng(ubicacion.latitud, ubicacion.longitud))
                            .title(ubicacion.nombre)
                            .snippet(ubicacion.detalle())
                    )
                }
            }
        }
    }

    override fun onStart() { super.onStart(); mapView.onStart() }
    override fun onResume() { super.onResume(); mapView.onResume() }
    override fun onPause() { mapView.onPause(); super.onPause() }
    override fun onStop() { mapView.onStop(); super.onStop() }
    override fun onLowMemory() { super.onLowMemory(); mapView.onLowMemory() }
    override fun onDestroy() { mapView.onDestroy(); super.onDestroy() }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}