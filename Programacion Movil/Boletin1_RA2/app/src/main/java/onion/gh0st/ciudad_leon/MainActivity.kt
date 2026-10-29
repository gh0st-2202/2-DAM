package onion.gh0st.ciudad_leon

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var btnJavi : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        hacerLayoutsInteractuables()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnJavi = findViewById<Button>(R.id.btn_javi)
        btnJavi.setOnClickListener {
            Toast.makeText(this,"PROBANDO",3).show()
        }
    }



    fun hacerLayoutsInteractuables() {
        val lytMonumentos = findViewById<LinearLayout>(R.id.lyt_monumentos)
        val lytNegocios   = findViewById<LinearLayout>(R.id.lyt_negocios)

        lytMonumentos.setOnClickListener {
            startActivity(Intent(this, MonumentosActivity::class.java))
        }

        lytNegocios.setOnClickListener {
            startActivity(Intent(this, NegociosActivity::class.java))
        }
    }
}