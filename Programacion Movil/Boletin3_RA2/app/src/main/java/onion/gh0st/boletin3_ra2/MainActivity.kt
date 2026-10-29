package onion.gh0st.boletin3_ra2

import android.content.Intent
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import onion.gh0st.boletin3_ra2.ventanasBotones.EventosActivity
import onion.gh0st.boletin3_ra2.ventanasBotones.MapActivity
import onion.gh0st.boletin3_ra2.ventanasBotones.MonumentosActivity
import onion.gh0st.boletin3_ra2.ventanasBotones.RestaurantesActivity
import kotlin.math.sqrt

class MainActivity : AppCompatActivity(), SensorEventListener {

    lateinit var crdLugares : CardView
    lateinit var crdComida : CardView
    lateinit var crdEventos : CardView
    lateinit var crdMapa : CardView
    private lateinit var main : ConstraintLayout
    private lateinit var sensorManager : SensorManager
    private var sensorLuz : Sensor? = null
    private var sensorAcelerometro : Sensor? = null
    private var modoActual : Int = AppCompatDelegate.getDefaultNightMode()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensorLuz = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        sensorAcelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        initComponets()
        enableListeners()
    }

    /**
     * Inicia los componentes que usaremos en la aplicacion
     */
    fun initComponets() {
        crdLugares = findViewById(R.id.crdLugares)
        crdComida = findViewById(R.id.crdComida)
        crdEventos = findViewById(R.id.crdEventos)
        crdMapa = findViewById(R.id.crdMapa)
        main = findViewById(R.id.main)
    }

    /**
     * Crea los listeners para que los "botones" sean interactuables
     */
    private fun enableListeners() {
        crdLugares.setOnClickListener {
            startActivity(Intent(this, MonumentosActivity::class.java))
        }
        crdComida.setOnClickListener {
            startActivity(Intent(this, RestaurantesActivity::class.java))
        }
        crdEventos.setOnClickListener {
            startActivity(Intent(this, EventosActivity::class.java))
        }
        crdMapa.setOnClickListener {
            startActivity(Intent(this, MapActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        sensorLuz?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
        sensorAcelerometro?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) { }

    /**
     * Este metodo cambia a modo claro u oscuro dependiendo de los datos del sensor
     * También cambia el fondo a rojo durante 1 segundo si detecta una sacudida
     */
    override fun onSensorChanged(event: SensorEvent) {
        when (event.sensor.type) {

            // Comprueba los datos del sensor de luz y cambia entre modo claro y oscuro
            Sensor.TYPE_LIGHT -> {
                val lux = event.values[0]
                val nuevoModo = if (lux < 5f) {
                    AppCompatDelegate.MODE_NIGHT_YES
                } else {
                    AppCompatDelegate.MODE_NIGHT_NO
                }
                if (nuevoModo != modoActual) {
                    modoActual = nuevoModo
                    AppCompatDelegate.setDefaultNightMode(nuevoModo)
                }
            }

            // Comprueba los datos del acelerometro y ejecuta la funcion encargada de cambiar el fondo a rojo
            Sensor.TYPE_ACCELEROMETER -> {
                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]
                val magnitud = sqrt(x * x + y * y + z * z)

                if (magnitud > 30f) {
                    onShake()
                }
            }
        }
    }

    /**
     * Esta funcion es la encargada de cambiar el color del fondo a rojo durante 1 segundo
     * y despues devolverlo a su color original
     */
    private fun onShake() {
        val colorOriginal = ContextCompat.getColor(this, R.color.fondo)
        main.setBackgroundColor(Color.RED)
        main.postDelayed({
            main.setBackgroundColor(colorOriginal)
        }, 1000)
    }
}