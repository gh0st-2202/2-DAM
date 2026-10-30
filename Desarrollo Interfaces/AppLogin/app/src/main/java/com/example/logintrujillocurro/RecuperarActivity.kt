package com.example.logintrujillocurro

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged

class RecuperarActivity : AppCompatActivity() {

    private var usuarioValido: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar)

        val etUsuarioRecuperar = findViewById<EditText>(R.id.etUsuarioRecuperar)
        val btnRecuperar = findViewById<Button>(R.id.btnRecuperarClave)
        val tvResultado = findViewById<TextView>(R.id.tvResultadoRecuperar)
        val llCambioPassword = findViewById<LinearLayout>(R.id.llCambioPassword)
        val etNuevaPassword = findViewById<EditText>(R.id.etNuevaPasswordRecuperar)
        val etRepetirPassword = findViewById<EditText>(R.id.etRepetirPasswordRecuperar)
        val btnCambiar = findViewById<Button>(R.id.btnCambiarPassword)
        val btnVolver = findViewById<Button>(R.id.btnVolverRecuperar)

        // Si el usuario cambia el nombre, invalidamos la sesión de recuperación
        etUsuarioRecuperar.doAfterTextChanged { editable ->
            val textoActual = editable?.toString()?.trim().orEmpty()
            if (usuarioValido != null &&
                !textoActual.equals(usuarioValido, ignoreCase = true)) {
                usuarioValido = null
                llCambioPassword.visibility = View.GONE
                tvResultado.text = ""
            }
        }

        // --- PASO 1: buscar el usuario ---
        btnRecuperar.setOnClickListener {
            val usuario = etUsuarioRecuperar.text.toString().trim()

            if (usuario.isEmpty()) {
                tvResultado.text = getString(R.string.escribir_usuario)
                llCambioPassword.visibility = View.GONE
                usuarioValido = null
                return@setOnClickListener
            }

            val password = RepositorioUsuarios.obtenerPassword(usuario)

            if (password == null) {
                usuarioValido = null
                tvResultado.text = getString(R.string.usuario_no_registrado_fmt, usuario)
                llCambioPassword.visibility = View.GONE
            } else {
                usuarioValido = usuario
                tvResultado.text = getString(R.string.password_actual_fmt, password)
                llCambioPassword.visibility = View.VISIBLE
            }
        }

        // --- PASO 2: cambiar la contraseña ---
        btnCambiar.setOnClickListener {
            val usuario = usuarioValido ?: return@setOnClickListener
            val p1 = etNuevaPassword.text.toString().trim()
            val p2 = etRepetirPassword.text.toString().trim()

            when {
                p1.isEmpty() || p2.isEmpty() ->
                    toast(getString(R.string.completa_dos_campos))

                p1.length < 6 ->
                    toast(getString(R.string.password_min_6))

                p1 != p2 ->
                    toast(getString(R.string.passwords_no_coinciden))

                RepositorioUsuarios.actualizarPassword(usuario, p1) -> {
                    toast(getString(R.string.password_actualizada))
                    finish()
                }

                else -> toast(getString(R.string.password_no_actualizada))
            }
        }

        btnVolver.setOnClickListener { finish() }
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}