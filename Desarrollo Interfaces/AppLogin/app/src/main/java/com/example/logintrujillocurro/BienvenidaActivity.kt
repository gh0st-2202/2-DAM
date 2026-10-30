package com.example.logintrujillocurro

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BienvenidaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bienvenida)

        val tvMensajeBienvenida = findViewById<TextView>(R.id.tvMensajeBienvenida)
        val btnCerrarSesion = findViewById<Button>(R.id.btnCerrarSesion)

        val usuarioRecibido = intent.getStringExtra("USUARIO_KEY")

        if (usuarioRecibido.isNullOrBlank()) {
            finish()
            return
        }

        tvMensajeBienvenida.text = getString(R.string.bienvenida_usuario, usuarioRecibido)

        // Cerrar sesión: volvemos al login limpiando el back stack
        btnCerrarSesion.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}