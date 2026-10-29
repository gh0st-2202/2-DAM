package onion.gh0st.boletin2_ra2

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.card.MaterialCardView
import com.google.android.material.slider.Slider

class MainActivity : AppCompatActivity() {

    private lateinit var grpSexo : MaterialButtonToggleGroup
    private lateinit var sldAltura : Slider
    private lateinit var sldPeso : Slider
    private lateinit var txtAltura : TextView
    private lateinit var txtPeso : TextView
    private lateinit var txtIMC : TextView
    private lateinit var txtCategoria : TextView
    private lateinit var crdResultado : MaterialCardView

    private var sexoSeleccionado = "hombre"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        iniciarComponentes()
        configurarListeners()
        actualizarTodo()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    /**
     * Esta función se encarga de cargar los componentes de la pantalla
     */
    private fun iniciarComponentes() {
        grpSexo = findViewById(R.id.grpSexo)
        sldAltura = findViewById(R.id.sldAltura)
        sldPeso = findViewById(R.id.sldPeso)
        txtAltura = findViewById(R.id.txtAltura)
        txtPeso = findViewById(R.id.txtPeso)
        txtIMC = findViewById(R.id.txtIMC)
        txtCategoria = findViewById(R.id.txtCategoria)
        crdResultado = findViewById(R.id.crdResultado)
    }

    /**
     * Asocia los listeners de sliders y toggle group
     */
    private fun configurarListeners() {
        sldAltura.addOnChangeListener { _, _, _ -> actualizarTodo() }
        sldPeso.addOnChangeListener { _, _, _ -> actualizarTodo() }

        grpSexo.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                sexoSeleccionado = when (checkedId) {
                    R.id.btnMujer -> "mujer"
                    else -> "hombre"
                }
                actualizarTodo()
            }
        }
    }

    /**
     * Actualiza los textos de los sliders, calcula el IMC y colorea el resultado
     */
    private fun actualizarTodo() {
        val alturaCm = sldAltura.value
        val pesoKg = sldPeso.value

        var categoria = getString(R.string.peso_normal)
        var colorRes = R.color.peso_normal

        // Textos de entrada
        txtAltura.text = "${getString(R.string.txt_altura)}: ${alturaCm.toInt()} cm"
        txtPeso.text = "${getString(R.string.txt_peso)}: ${pesoKg.toInt()} kg"

        // Cálculo IMC = peso / altura^2
        val alturaM = alturaCm / 100f
        val imc = pesoKg / (alturaM * alturaM)
        txtIMC.text = "%.2f".format(imc)

        // Pone un texto y un color segun el IMC obtenido
        when {
            imc < 18.5f -> { categoria = getString(R.string.bajo_peso)
                             colorRes = R.color.bajo_peso }
            imc < 25f -> { categoria = getString(R.string.peso_normal)
                           colorRes = R.color.peso_normal }
            imc < 30f -> { categoria = getString(R.string.sobrepeso)
                           colorRes = R.color.sobrepeso }
            else -> { categoria = getString(R.string.obesidad)
                      colorRes = R.color.obesidad }
        }

        val color = ContextCompat.getColor(this, colorRes)
        txtCategoria.text = categoria
        txtCategoria.setTextColor(color)
        crdResultado.strokeColor = color
    }
}