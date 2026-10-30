package com.example.logintrujillocurro

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegistroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val etNuevoUsuario = findViewById<EditText>(R.id.etNuevoUsuario)
        val etNuevaPassword = findViewById<EditText>(R.id.etNuevaPassword)
        val etRepetirPassword = findViewById<EditText>(R.id.etRepetirPassword)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarRegistro)
        val btnVolver = findViewById<Button>(R.id.btnVolverRegistro)

        btnGuardar.setOnClickListener {
            val usuario = etNuevoUsuario.text.toString().trim()
            val pass = etNuevaPassword.text.toString().trim()
            val pass2 = etRepetirPassword.text.toString().trim()

            when {
                usuario.isEmpty() || pass.isEmpty() || pass2.isEmpty() ->
                    toast(getString(R.string.campos_vacios))

                usuario.length < 3 ->
                    toast(getString(R.string.usuario_min_3))

                usuario.contains(" ") ->
                    toast(getString(R.string.usuario_sin_espacios))

                pass.length < 6 ->
                    toast(getString(R.string.password_min_6))

                pass != pass2 ->
                    toast(getString(R.string.passwords_no_coinciden))

                RepositorioUsuarios.existe(usuario) ->
                    toast(getString(R.string.usuario_ya_existe))

                RepositorioUsuarios.registrar(usuario, pass) -> {
                    toast(getString(R.string.usuario_registrado_fmt, usuario))
                    finish()
                }

                else -> toast(getString(R.string.no_se_pudo_registrar))
            }
        }

        btnVolver.setOnClickListener { finish() }
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}